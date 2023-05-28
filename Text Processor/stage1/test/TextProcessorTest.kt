import org.hyperskill.hstest.dynamic.DynamicTest
import org.hyperskill.hstest.exception.outcomes.WrongAnswer
import org.hyperskill.hstest.stage.StageTest
import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testing.TestedProgram

data class Detail(val sentence: String, val length: Int, val mostUsed: Pair<List<Char>, Int>?) {
    override fun toString(): String {
        val mostUsedChar = mostUsed?.first?.joinToString(
            ", ",
            "Most used character${if (mostUsed.first.size != 1) "s: " else ": "}",
            " (used ${mostUsed.second} times)"
        ) ?: "The count of all characters is the same"
        return "Number of characters: $length\n$mostUsedChar"
    }
}

class TextProcessorTest : StageTest<Any>() {

    val common = arrayOf(
        Detail("The quick brown fox jumps over the lazy dog.", 44, Pair(listOf('o'), 4)),
        Detail("She sells seashells by the seashore.", 36, Pair(listOf('e', 's'), 7)),
    )

    val oneMostUsed = arrayOf(
        Detail("The quick brown fox jumps over the lazy dog.", 44, Pair(listOf('o'), 4)),
        Detail("Peter Piper picked a peck of pickled peppers.", 45, Pair(listOf('e'), 8)),
        Detail("How much wood would a woodchuck chuck if a woodchuck could chuck wood?", 70, Pair(listOf('o'), 11)),
        Detail("I scream, you scream, we all scream for ice cream!", 50, Pair(listOf('e'), 6)),
        Detail("The sun is shining brightly in the clear blue sky.", 50, Pair(listOf('i'), 5)),
        Detail("The rain began to fall gently, refreshing the earth below.", 58, Pair(listOf('e'), 8)),
        Detail("The birds chirped melodiously as the sun rose over the horizon.", 63, Pair(listOf('e'), 7)),
        Detail("The old man sat on the park bench, feeding breadcrumbs to the ducks.", 68, Pair(listOf('e'), 7)),
        Detail("The marathon runner crossed the finish line, breathless but triumphant.", 71, Pair(listOf('e'), 7)),
        Detail("The little girl twirled in her pretty pink dress, giggling with joy.", 68, Pair(listOf('i'), 8)),
        Detail("The smell of freshly baked cookies filled the kitchen, tempting everyone.", 73, Pair(listOf('e'), 12)),
        Detail("The sound of waves crashing against the shore provided a soothing ambiance.", 75, Pair(listOf('a'), 7)),
        Detail(
            "The teacher patiently explained the complex math problem until it finally clicked.",
            82,
            Pair(listOf('e'), 10)
        ),
        Detail(
            "The actor delivered a captivating performance, leaving the audience in awe.",
            75,
            Pair(listOf('e'), 11)
        ),
        Detail("The flowers bloomed in vibrant colors, painting the garden with beauty.", 71, Pair(listOf('e'), 6)),
        Detail(
            "The child built a sandcastle on the beach, creating a mini kingdom of their own.",
            80,
            Pair(listOf('i'), 7)
        ),
        Detail(
            "The magician performed mind-bending tricks that left everyone amazed and puzzled.",
            81,
            Pair(listOf('e'), 10)
        ),
        Detail(
            "The scent of freshly brewed coffee wafted through the café, enticing customers.",
            79,
            Pair(listOf('e'), 11)
        ),
        Detail(
            "The sun dipped below the horizon, painting the sky in a breathtaking display of colors.",
            87,
            Pair(listOf('i'), 7)
        ),
        Detail(
            "The friends gathered around a campfire, sharing stories and roasting marshmallows.",
            82,
            Pair(listOf('a'), 9)
        )
    )

    val manyMostUsed = arrayOf(
        Detail("She sells seashells by the seashore.", 36, Pair(listOf('e', 's'), 7)),
        Detail("The cat in the hat wore a red and white striped hat.", 52, Pair(listOf('e', 't'), 6)),
        Detail(
            "The mountain peak stood majestically, covered in a blanket of snow.",
            67,
            Pair(listOf('e', 'o', 'a'), 6)
        ),
        Detail(
            "The bookshelf was filled with novels, each holding its own unique story.",
            72,
            Pair(listOf('e', 'o'), 6)
        ),
        Detail(
            "The guitar player strummed the strings, filling the room with melodious tunes.",
            78,
            Pair(listOf('e', 't'), 7)
        ),
        Detail("The sky is clear today.", 23, Pair(listOf('e', 's', 'y', 'a'), 2)),
        Detail("The moon is beautiful tonight.", 30, Pair(listOf('o', 'i', 't'), 3)),
        Detail("In the cosmic dance of stars, constellations tell ancient stories.", 66, Pair(listOf('t', 's'), 7)),
        Detail("The moonlight paints the world in shades of silver.", 51, Pair(listOf('h', 'e', 'o', 'i', 's'), 4)),
        Detail("The scent of rain on a parched earth is intoxicating.", 53, Pair(listOf('n', 'a', 'i'), 5)),
        Detail("A single candle can ignite a thousand dreams.", 45, Pair(listOf('n', 'a'), 5)),
        Detail("In the realm of words, stories hold the power to change the world.", 66, Pair(listOf('e', 'o'), 7)),
        Detail("A single act of kindness ripples through the world.", 51, Pair(listOf('s', 'e'), 4)),
        Detail("Creativity knows no bounds.", 27, Pair(listOf('n', 'o'), 3)),
        Detail("The mountains are calling.", 26, Pair(listOf('n', 'a'), 3)),
        Detail("Writing is a form of self-expression.", 37, Pair(listOf('i', 's'), 4)),
        Detail("Programming is an art.", 22, Pair(listOf('r', 'a'), 3)),
        Detail("Coffee is my fuel.", 18, Pair(listOf('f', 'e'), 3)),
        Detail("Learning new things is exciting.", 32, Pair(listOf('n', 'i'), 5)),
        Detail("Reading expands the mind.", 25, Pair(listOf('e', 'd', 'n'), 3)),
    )

    val noMostUsed = arrayOf(
        Detail("1234567890", 10, null),
        Detail("QWERTY", 6, null),
        Detail("qwerty", 6, null),
        Detail("abcdef", 6, null),
        Detail("ABCDEF", 6, null),
        Detail("Are you OK?", 11, null),
        Detail("It's nice.", 10, null),
        Detail("ASDFGHJK", 8, null),
        Detail("asdfghjk", 8, null),
        Detail("zxcvbnm", 7, null)
    )

    @DynamicTest()
    fun test1(): CheckResult {
        val program = TestedProgram()
        val output = program.start().trim()
        if (output != "Enter text in a single line (or exit):") throw WrongAnswer("Your prompt should match the following: Enter text in a single line (or exit):")

        return CheckResult.correct()
    }

    @DynamicTest(data = "common")
    fun test2(detail: Detail): CheckResult {
        val program = TestedProgram()
        program.start()
        val output = program.execute(detail.sentence).trim()
        if (!output.contains(detail.length.toString())) throw WrongAnswer("Number of characters is wrong, should be: ${detail.length}")

        return CheckResult.correct()
    }

    @DynamicTest(data = "common")
    fun test3(detail: Detail): CheckResult {
        val program = TestedProgram()
        program.start()
        val mostUsedChars = detail.mostUsed!!.first
        val output = program.execute(detail.sentence).trim()
        if (!output.contains(mostUsedChars.joinToString())) throw WrongAnswer("The most used char${if (mostUsedChars.size == 1) "" else "s"} found wrong, should be: ${mostUsedChars.joinToString()}")

        return CheckResult.correct()
    }

    @DynamicTest(data = "common")
    fun test4(detail: Detail): CheckResult {
        val program = TestedProgram()
        program.start()
        val frequency = detail.mostUsed!!.second
        val output = program.execute(detail.sentence).trim()
        if (!output.contains(frequency.toString())) throw WrongAnswer("The count of the most used char(s) found wrong, should be: $frequency")

        return CheckResult.correct()
    }

    @DynamicTest(data = "manyMostUsed")
    fun test5(detail: Detail): CheckResult {
        val program = TestedProgram()
        program.start()
        val output = program.execute(detail.sentence).trim()
        if (!output.contains("chars:")) throw WrongAnswer("When there are more than one most used characters you've to output 'chars:'!")

        return CheckResult.correct()
    }

    @DynamicTest(data = "oneMostUsed")
    fun test6(detail: Detail): CheckResult {
        val program = TestedProgram()
        program.start()
        val output = program.execute(detail.sentence).trim()
        if (!output.contains("char:")) throw WrongAnswer("When there is only one most used character you've to output 'char:'!")

        return CheckResult.correct()
    }

    @DynamicTest(data = "noMostUsed")
    fun test7(detail: Detail): CheckResult? {
        val program = TestedProgram()
        program.start()
        val output = program.execute(detail.sentence).trim()
        if (!output.contains("The count of all characters is the same")) throw WrongAnswer("When there is no most used character you've to output 'The count of all characters is the same'!")

        return CheckResult.correct()
    }

    @DynamicTest()
    fun test8(): CheckResult {
        val program = TestedProgram()
        program.start()
        val output = program.execute("exit").trim()
        if (!output.contains("Bye!")) throw WrongAnswer("Your program should print 'Bye!' after termination.")

        return CheckResult.correct()
    }
}
