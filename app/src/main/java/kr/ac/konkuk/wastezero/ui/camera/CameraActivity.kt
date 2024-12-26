package kr.ac.konkuk.wastezero.ui.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kr.ac.konkuk.wastezero.databinding.ActivityCameraBinding
import kr.ac.konkuk.wastezero.ui.ingredient.IngredientListActivity
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private lateinit var tflite: Interpreter

    val labels = listOf(
        "Button_mushroom_b", "Egg_b", "King trumpet mushroom", "Onion_b", "Pepper_b", "Potato_b", "Radish_b",
        "Tomato_b", "beef", "button mushroom", "cabbage", "carrot", "chicken", "cucumber", "cucumber_b", "egg",
        "enoki mushroom", "garlic", "ham", "onion", "oyster mushroom", "pepper", "pork", "potato", "radish", "salmon",
        "shiitake", "shrimp", "tahu", "tomato"
    )

    val labelTranslations = mapOf(
        "Button_mushroom_b" to "버튼 버섯",
        "Egg_b" to "계란",
        "King trumpet mushroom" to "새송이 버섯",
        "Onion_b" to "양파",
        "Pepper_b" to "고추",
        "Potato_b" to "감자",
        "Radish_b" to "무",
        "Tomato_b" to "토마토",
        "beef" to "소고기",
        "button mushroom" to "양송이 버섯",
        "cabbage" to "양배추",
        "carrot" to "당근",
        "chicken" to "닭고기",
        "cucumber" to "오이",
        "cucumber_b" to "오이",
        "egg" to "달걀",
        "enoki mushroom" to "팽이버섯",
        "garlic" to "마늘",
        "ham" to "햄",
        "onion" to "양파",
        "oyster mushroom" to "느타리버섯",
        "pepper" to "고추",
        "pork" to "돼지고기",
        "potato" to "감자",
        "radish" to "무",
        "salmon" to "연어",
        "shiitake" to "표고버섯",
        "shrimp" to "새우",
        "tahu" to "두부",
        "tomato" to "토마토"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 권한 확인 및 요청
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission()
        } else {
            initializeCamera()
        }
    }

    private fun requestCameraPermission() {
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            initializeCamera()
        } else {
            Toast.makeText(this, "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun initializeCamera() {
        try {
            tflite = Interpreter(loadModelFile("best-fp16-1.tflite"))
            launchCamera()
        } catch (e: Exception) {
            Toast.makeText(this, "TensorFlow Lite 모델 로드 실패: ${e.message}", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun launchCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val bitmap = data?.extras?.get("data") as? Bitmap
            if (bitmap != null) {
                val detectedIngredients = processImage(bitmap)
                navigateToIngredientList(detectedIngredients)
            }
        } else {
            Toast.makeText(this, "카메라 사용이 취소되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun processImage(bitmap: Bitmap): List<String> {
        val inputBuffer = preprocessImage(bitmap)
        val outputBuffer = Array(1) { Array(10647) { FloatArray(35) } }
        tflite.run(inputBuffer, outputBuffer)
        val results = postProcess(outputBuffer[0])
        return results.map { labelTranslations[it] ?: it }
    }

    private fun preprocessImage(bitmap: Bitmap): ByteBuffer {
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 416, 416, true)
        val buffer = ByteBuffer.allocateDirect(1 * 416 * 416 * 3 * 4).apply {
            order(ByteOrder.nativeOrder())
        }
        for (y in 0 until 416) {
            for (x in 0 until 416) {
                val pixel = resizedBitmap.getPixel(x, y)
                buffer.putFloat((pixel shr 16 and 0xFF) / 255.0f)
                buffer.putFloat((pixel shr 8 and 0xFF) / 255.0f)
                buffer.putFloat((pixel and 0xFF) / 255.0f)
            }
        }
        return buffer
    }

    private fun postProcess(output: Array<FloatArray>): List<String> {
        val detectedMap = mutableMapOf<String, Float>()
        for (box in output) {
            val confidence = box[4]
            if (confidence > 0.7f) {
                val scores = box.slice(5 until box.size)
                val maxScoreIndex = scores.indices.maxByOrNull { scores[it] } ?: -1
                if (maxScoreIndex in labels.indices) {
                    val label = labels[maxScoreIndex]
                    val translatedLabel = labelTranslations[label] ?: label
                    if (detectedMap[translatedLabel] == null || detectedMap[translatedLabel]!! < confidence) {
                        detectedMap[translatedLabel] = confidence
                    }
                }
            }
        }
        return detectedMap.keys.toList()
    }

    private fun navigateToIngredientList(ingredients: List<String>) {
        val intent = Intent(this, IngredientListActivity::class.java).apply {
            putStringArrayListExtra("detectedIngredients", ArrayList(ingredients))
        }
        startActivity(intent)
        finish()
    }

    private fun loadModelFile(fileName: String): ByteBuffer {
        val fileDescriptor = assets.openFd(fileName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
    }
}
