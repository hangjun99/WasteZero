package kr.ac.konkuk.wastezero.domain.entity

data class RecipeDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val recipeUrl: String,
) {
    companion object {
        val recipeList: List<RecipeDetail> = listOf(
            RecipeDetail(
                1,
                "백선생표 감자전",
                "https://recipe1.ezmember.co.kr/cache/recipe/2016/06/25/5c69c55183afa9c4cf1be4e417668a031.jpg",
                "https://m.10000recipe.com/recipe/6850115",
            ),
            RecipeDetail(
                2,
                "감자채볶음 요리",
                "https://recipe1.ezmember.co.kr/cache/recipe/2015/08/13/a3326dc207ac0234154c1276b721861b1.jpg",
                "https://m.10000recipe.com/recipe/4007561",
            ),
            RecipeDetail(
                3,
                "보들보들 백선생 계란말이 따라만들기",
                "https://recipe1.ezmember.co.kr/cache/recipe/2015/11/12/006d3080be97cb3328a20931e7eafddc1.jpg",
                "https://m.10000recipe.com/recipe/6838011",
            ),
            RecipeDetail(
                4,
                "식당에서 먹던 맛과 비주얼 그대로, 폭탄 계란찜 만드는 법",
                "https://recipe1.ezmember.co.kr/cache/recipe/2017/07/06/c807784ab41809f624a6a4a7466cd5bd1.jpg",
                "https://m.10000recipe.com/recipe/6872350",
            ),
            RecipeDetail(
                5,
                "매콤달콤 고추장 돼지불고기",
                "https://recipe1.ezmember.co.kr/cache/recipe/2017/05/06/9948fba54b6c6b619f97b61a2ba61d461.jpg",
                "https://m.10000recipe.com/recipe/6869806",
            ),
            RecipeDetail(
                6,
                "꽈리고추멸치볶음 밑반찬 만들기",
                "https://recipe1.ezmember.co.kr/cache/recipe/2017/08/29/a4c3aeef99d1bb75ebaaa373d5796f0a1.jpg",
                "https://m.10000recipe.com/recipe/6875568",
            ),
            RecipeDetail(
                7,
                "닭볶음탕 진짜진짜 황금레시피 알려 드려요~~^^",
                "https://recipe1.ezmember.co.kr/cache/recipe/2017/09/12/e082636233ee03a012fc656d428df5971.jpg",
                "https://m.10000recipe.com/recipe/6876357",
            ),
            RecipeDetail(
                8,
                "당근으로 반찬 만들기 : 당근라페",
                "https://recipe1.ezmember.co.kr/cache/recipe/2019/08/09/b18d8a6c5fc8cda7ddc69ba37f15ada71.jpg",
                "https://m.10000recipe.com/recipe/6917320",
            ),
            RecipeDetail(
                9,
                "돼지고기 김치찌개 맛내는 비법",
                "https://recipe1.ezmember.co.kr/cache/recipe/2015/08/25/a01d013a6b6f9d526c43f4659db2cd61.jpg",
                "https://m.10000recipe.com/recipe/1785098"
            ),
            RecipeDetail(
                10,
                "돼지고기 장조림 만들기 국물까지 맛있어요",
                "https://recipe1.ezmember.co.kr/cache/recipe/2017/09/16/adbf9a20616e14cdb1c16c9891b2b84b1.jpg",
                "https://m.10000recipe.com/recipe/6876570"
            ),
            RecipeDetail(
                11,
                "두부김치 황금레시피~!! 고기없어도 괜찮아요~",
                "https://recipe1.ezmember.co.kr/cache/recipe/2017/07/10/06822a9fe6e7213aeb0e3fb0ff19ee3c1.jpg",
                "https://m.10000recipe.com/recipe/6872475"
            ),
            RecipeDetail(
                12,
                "두부조림 만들기",
                "https://recipe1.ezmember.co.kr/cache/recipe/2015/08/05/4cce84e6f70767fb22e8ea35b772dd781.jpg",
                "https://m.10000recipe.com/recipe/4588059"
            ),
            RecipeDetail(
                13,
                "무생채 만드는 법, 절이지 않고 10분 만에 휘리릭 ~",
                "https://recipe1.ezmember.co.kr/cache/recipe/2018/07/29/52046ea43391de69233f594b0b52bb311.JPG",
                "https://m.10000recipe.com/recipe/6893285"
            ),
        )

        val potatoList: List<RecipeDetail> = listOf(
            RecipeDetail(
                1,
                "백선생표 감자전",
                "https://recipe1.ezmember.co.kr/cache/recipe/2016/06/25/5c69c55183afa9c4cf1be4e417668a031.jpg",
                "https://m.10000recipe.com/recipe/6850115",
            ),
            RecipeDetail(
                2,
                "감자채볶음 요리",
                "https://recipe1.ezmember.co.kr/cache/recipe/2015/08/13/a3326dc207ac0234154c1276b721861b1.jpg",
                "https://m.10000recipe.com/recipe/4007561",
            ),
            RecipeDetail(
                3,
                "닭볶음탕 진짜진짜 황금레시피 알려 드려요~~^^",
                "https://recipe1.ezmember.co.kr/cache/recipe/2017/09/12/e082636233ee03a012fc656d428df5971.jpg",
                "https://m.10000recipe.com/recipe/6876357",
            ),
        )

    }
}
