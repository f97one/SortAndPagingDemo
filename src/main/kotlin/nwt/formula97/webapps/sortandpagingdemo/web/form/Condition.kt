package nwt.formula97.webapps.sortandpagingdemo.web.form

import org.springframework.util.StringUtils
import java.io.Serializable
import java.net.URLEncoder
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

data class Condition(var cityCode: String?, var cityName: String?): Serializable {
    companion object {
        private const val serialVersionUID: Long = -1L
    }

    fun encodedCityCode(charset: Charset): String? {
        return if (StringUtils.hasLength(this.cityCode)) URLEncoder.encode(this.cityCode, charset) else null
    }

    fun encodedCityCode(): String? {
        return encodedCityCode(StandardCharsets.UTF_8)
    }

    fun encodedCityName(charset: Charset): String? {
        return if (StringUtils.hasLength(this.cityName)) URLEncoder.encode(this.cityName, charset) else null
    }

    fun encodedCityName(): String? {
        return encodedCityName(StandardCharsets.UTF_8)
    }
}