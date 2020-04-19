package nwt.formula97.webapps.sortandpagingdemo.repository

import nwt.formula97.webapps.sortandpagingdemo.domain.City
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CityRepository: JpaRepository<City, String> {

    fun findCitiesByCityCodeIsLike(@Param("cityCode") cityCode: String, pageable: Pageable): Page<City>

    fun findCitiesByCityNameIsLike(@Param("cityName") cityName: String, pageable: Pageable): Page<City>

    fun findCitiesByCityCodeIsLikeAndCityNameIsLike(@Param("cityCode") cityCode: String, @Param("cityName") cityName: String, pageable: Pageable): Page<City>
}