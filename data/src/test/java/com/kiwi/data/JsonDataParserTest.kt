package com.kiwi.data

import com.kiwi.data.entities.BaseLiquor
import com.kiwi.data.entities.BaseLiquorType
import com.kiwi.data.entities.IBACategoryType
import com.kiwi.data.entities.IBACocktail
import kotlinx.serialization.SerializationException
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.junit.Assert.assertThrows
import org.junit.Test

private const val BASE_LIQUORS_FILENAME = "test_base_liquors.json"
private const val IBA_COCKTAILS_FILENAME = "test_iba_cocktails.json"
private const val MALFORMED_FILENAME = "malformed_json_data.json"

class JsonDataParserTest {

    @Test
    fun `test parse base liquors data`() {
        val bufferedSource = this.javaClass.classLoader!!.getResource(BASE_LIQUORS_FILENAME)
            .openStream()
            .source()
            .buffer()

        val data = DataJsonParser.parseBaseLiquorData(bufferedSource)
        assertThat(data.size, Is(equalTo(5)))

        val fakeBaseLiquor = BaseLiquor(
            id = "179",
            name = "Dark Rum",
            baseLiquor = BaseLiquorType.RUM,
        )
        assertThat(data[0], Is(equalTo(fakeBaseLiquor)))
    }

    @Test
    fun `test parse iba cocktails data`() {
        val bufferedSource = this.javaClass.classLoader!!.getResource(IBA_COCKTAILS_FILENAME)
            .openStream()
            .source()
            .buffer()

        val data = DataJsonParser.parseIBACocktailData(bufferedSource)
        assertThat(data.size, Is(equalTo(90)))

        val fakeIBACocktail = IBACocktail(
            id = "11014",
            name = "Alexander",
            iba = IBACategoryType.THE_UNFORGETTABLES,
        )
        assertThat(data[0], Is(equalTo(fakeIBACocktail)))
    }

    @Test
    fun `test malformed json`() {
        val bufferedSource = this.javaClass.classLoader!!.getResource(MALFORMED_FILENAME)
            .openStream()
            .source()
            .buffer()

        assertThrows(SerializationException::class.java) {
            DataJsonParser.parseBaseLiquorData(bufferedSource)
        }
        assertThrows(SerializationException::class.java) {
            DataJsonParser.parseIBACocktailData(bufferedSource)
        }
    }
}
