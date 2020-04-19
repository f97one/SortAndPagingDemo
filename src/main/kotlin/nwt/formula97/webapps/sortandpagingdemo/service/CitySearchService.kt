package nwt.formula97.webapps.sortandpagingdemo.service

import nwt.formula97.webapps.sortandpagingdemo.domain.City
import nwt.formula97.webapps.sortandpagingdemo.repository.CityRepository
import nwt.formula97.webapps.sortandpagingdemo.web.form.Condition
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class CitySearchService(private val cityRepository: CityRepository) {

    fun searchCity(condition: Condition?, pageable: Pageable): Page<City> {
        return if (condition == null || (StringUtils.isEmpty(condition.cityCode) && StringUtils.isEmpty(condition.cityName))) {
            cityRepository.findAll(pageable)
        } else if (StringUtils.hasLength(condition.cityCode) && StringUtils.isEmpty(condition.cityName)) {
            cityRepository.findCitiesByCityCodeIsLike("${condition.cityCode!!}%", pageable)
        } else if (StringUtils.isEmpty(condition.cityCode) && StringUtils.hasLength(condition.cityName)) {
            cityRepository.findCitiesByCityNameIsLike("${condition.cityName!!}%", pageable)
        } else {
            cityRepository.findCitiesByCityCodeIsLikeAndCityNameIsLike("${condition.cityCode!!}%", "${condition.cityName!!}%", pageable)
        }
    }
}