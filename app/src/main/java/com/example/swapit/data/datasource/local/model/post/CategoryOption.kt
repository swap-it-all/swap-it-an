package com.example.swapit.data.datasource.local.model.post

enum class CategoryOption(
    val id: Int,
    val option: String,
) {
    ELECTRONICS(1, "전자기기"),
    APPLIANCES(2, "가전제품"),
    FURNITURE(3, "가구"),
    CLOTHING(4, "의류/패션"),
    BEAUTY(5, "뷰티/화장품"),
    SPORTS(6, "스포츠/레저"),
    TOYS(7, "장난감/키즈"),
    BOOKS(8, "책/문구"),
    AUTOMOTIVE(9, "자동차/부품"),
    PET(10, "반려동물용품"),
    GROCERIES(11, "식품/음료"),
    HOBBY(12, "취미/게임/음반"),
    MISC(13, "기타"),
}
