package kr.ac.konkuk.wastezero.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class RecipeResponse(
    val COOKRCP01: RecipeDetails // 최상위 레벨
)

data class RecipeDetails(
    val row: List<Recipe> // 실제 레시피 목록
)

@Parcelize
data class Recipe(
    val RCP_NM: String,           // 레시피 이름
    val RCP_PARTS_DTLS: String,   // 재료 정보
    val RCP_WAY2: String,         // 조리 방법
    val ATT_FILE_NO_MAIN: String, // 대표 이미지 URL
    val ATT_FILE_NO_MK: String,   // 요리 이미지 URL
    val MANUAL01: String?,
    val MANUAL_IMG01: String?,
    val MANUAL02: String?,
    val MANUAL_IMG02: String?,
    val MANUAL03: String?,
    val MANUAL_IMG03: String?,
    val MANUAL04: String?,
    val MANUAL_IMG04: String?,
    val MANUAL05: String?,
    val MANUAL_IMG05: String?,
    val MANUAL06: String?,
    val MANUAL_IMG06: String?,
    val MANUAL07: String?,
    val MANUAL_IMG07: String?,
    val MANUAL08: String?,
    val MANUAL_IMG08: String?,
    val MANUAL09: String?,
    val MANUAL_IMG09: String?,
    val MANUAL10: String?,
    val MANUAL_IMG10: String?,
    val MANUAL11: String?,
    val MANUAL_IMG11: String?,
    val MANUAL12: String?,
    val MANUAL_IMG12: String?,
    val MANUAL13: String?,
    val MANUAL_IMG13: String?,
    val MANUAL14: String?,
    val MANUAL_IMG14: String?,
    val MANUAL15: String?,
    val MANUAL_IMG15: String?,
    val MANUAL16: String?,
    val MANUAL_IMG16: String?,
    val MANUAL17: String?,
    val MANUAL_IMG17: String?,
    val MANUAL18: String?,
    val MANUAL_IMG18: String?,
    val MANUAL19: String?,
    val MANUAL_IMG19: String?,
    val MANUAL20: String?,
    val MANUAL_IMG20: String?
) : Parcelable
