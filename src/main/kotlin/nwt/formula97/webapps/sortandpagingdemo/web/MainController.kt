package nwt.formula97.webapps.sortandpagingdemo.web

import nwt.formula97.webapps.sortandpagingdemo.domain.City
import nwt.formula97.webapps.sortandpagingdemo.domain.Pagination
import nwt.formula97.webapps.sortandpagingdemo.service.CitySearchService
import nwt.formula97.webapps.sortandpagingdemo.web.form.Condition
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
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
             @RequestParam(value = "cityName", required = false) cityName: String?,
             @RequestParam(value = "sortItem", required = false) sortItem: String?,
             @RequestParam(value = "direction", required = false) direction: String?,
             @RequestParam(value = "sortDirection", required = false) sortDirection: String?): String {

        var sort: Sort? = null
        if (StringUtils.hasLength(sortDirection)) {
            val sd = if (sortDirection == Sort.Direction.ASC.name) Sort.Direction.ASC.name else Sort.Direction.DESC.name
            val si: String
            if (StringUtils.hasLength(sortItem)) {
                si = sortItem!!
                model.addAttribute("sortItem", si)
            } else {
                si = "cityCode"
            }
            sort = Sort.by(Sort.Direction.fromString(sd), si)

            model.addAttribute("sortDirection", sd)
            model.addAttribute("direction", sd)
        } else if (StringUtils.hasLength(sortItem) && StringUtils.hasLength(direction)) {
            val dir = if (direction == Sort.Direction.DESC.name) Sort.Direction.ASC.name else Sort.Direction.DESC.name
            val searchDir = if (direction == Sort.Direction.DESC.name) Sort.Direction.DESC else Sort.Direction.ASC
            model.addAttribute("sortItem", sortItem)
            model.addAttribute("direction", dir)
            model.addAttribute("sortDirection", direction)
            val si = if (sortItem == "prefName") "pref.prefName" else sortItem
            sort = Sort.by(searchDir, si)
        }

        val condition = Condition(cityCode, cityName)
        val p: Pageable = if (sort == null) {
            pageable
        } else {
            PageRequest.of(pageable.pageNumber, pageable.pageSize, sort)
        }
        val cities: Page<City> = citySearchService.searchCity(condition, p)
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