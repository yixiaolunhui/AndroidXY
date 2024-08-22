import com.github.promeg.pinyinhelper.Pinyin

/**
 * 关键字排序
 */
fun main() {
    val testData = mutableListOf(
        "test0901",
        "test0902",
        "test0905",
        "dest0905",
        "dest091",
        "test092",
        "test090吧",
        "test090a",
        "上海路",
        "南京路",
        "北京路",
        "北路北",
        "南京南",
        "南京南@！##￥",
        "南京北",
        "北京南",
        "周总理",
        "欧豪"
    )

    val keyword = "t" // 示例关键字

    // 调用封装的排序方法，加入关键字
    val sortedStrings = sortStringsByPinyin(testData, keyword)

    // 打印排序结果
    println(sortedStrings)
}

// 封装的排序方法，接收待排序的列表和关键字，返回排序好的列表
fun sortStringsByPinyin(strings: List<String>, keyword: String): List<String> {
    return strings.filter { it.contains(keyword) }
        .map { str -> Triple(str, str.indexOf(keyword, ignoreCase = true), generateSortKey(str)) } // 生成关键字位置及排序键
        .sortedWith(compareBy<Triple<String, Int, String>> { it.second.takeIf { pos -> pos != -1 } ?: Int.MAX_VALUE } // 先按关键字位置排序，关键字不存在时设为最大值
            .thenBy { it.third }) // 然后按拼音或字母顺序排序
        .map { it.first } // 排序后恢复原始字符串
}

// 生成排序用的键，所有字母转为小写，中文转为拼音首字母的小写
fun generateSortKey(s: String): String {
    return s.flatMap { char ->
        if (char.isDigit() || char.isLowerCase() || char.isUpperCase()) {
            listOf(char.lowercaseChar()) // 对字母、数字直接转换为小写
        } else if (Pinyin.isChinese(char)) {
            getPinyinFirstLetter(char).toList() // 对中文字符转换为拼音首字母
        } else {
            listOf(char) // 其他非字母、数字、中文的字符
        }
    }.joinToString("") // 将列表转换为字符串
}

// 获取中文字符的拼音首字母
fun getPinyinFirstLetter(chineseChar: Char): String {
    return if (Pinyin.isChinese(chineseChar)) {
        Pinyin.toPinyin(chineseChar).substring(0, 1).lowercase()
    } else {
        chineseChar.toString()
    }
}
