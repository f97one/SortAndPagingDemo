package nwt.formula97.webapps.sortandpagingdemo.web

import nwt.formula97.webapps.sortandpagingdemo.domain.City
import nwt.formula97.webapps.sortandpagingdemo.domain.Pagination
import nwt.formula97.webapps.sortandpagingdemo.service.CitySearchService
import nwt.formula97.webapps.sortandpagingdemo.web.form.Condition
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class MainController {
    @Autowired
    private lateinit var citySearchService: CitySearchService

    @ModelAttribute(name = "condition")
    private fun setUpConditionForm(): Condition {
        return Condition(null, null)
    }

    @GetMapping("/")
    fun show(model: Model, @PageableDefault pageable: Pageable,
             @RequestParam(value = "cityCode", required = false) cityCode: String?,
             @RequestParam(value = "cityName", required = false) cityName: String?): String {

        val condition = Condition(cityCode, cityName)
        val cities: Page<City> = citySearchService.searchCity(condition, pageable)
        val page = Pagination(cities)
        model.addAttribute("cityList", cities)
        model.addAttribute("page", page)
        model.addAttribute("condition", condition)

        return "/sorted"
    }

    @PostMapping("/search")
    fun search(model: Model, @PageableDefault pageable: Pageable, condition: Condition?): String {
        // クエリパラメータを組み立てる
        val b: StringBuilder = StringBuilder("redirect:/?page=${pageable.pageNumber}")

        if (condition != null) {
            if (StringUtils.hasLength(condition.cityCode)) {
                b.append("&cityCode=${condition.encodedCityCode()}")
            }
            if (StringUtils.hasLength(condition.cityName)) {
                b.append("&cityName=${condition.encodedCityName()}")
            }
        }

        return b.toString()
    }

}