import com.github.promeg.pinyinhelper.Pinyin

fun main() {
    val strings = listOf("苹果", "123", "banana", "苹果园", "abc", "22", "北京", "100", "car", "Apple", "Car")

    // 调用封装的排序方法
    val sortedStrings = sortStringsByPinyin(strings)

    // 打印排序结果
    println(sortedStrings)
}

// 封装的排序方法，接收待排序的列表，返回排序好的列表
fun sortStringsByPinyin(strings: List<String>): List<String> {
    return strings
        .map { str -> Pair(str, generateSortKey(str)) } // 为每个字符串生成排序键
        .sortedWith(compareBy { it.second }) // 根据排序键排序
        .map { it.first } // 取出排序后的原始字符串
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
