package nwt.formula97.webapps.sortandpagingdemo.domain

import javax.persistence.*

@Entity(name = "city")
data class City(
        @Id
        @Column(name = "city_code", length = 6)
        var cityCode: String,
        @ManyToOne(targetEntity = Prefecture::class)
        @JoinColumn(name = "pref_id")
        var pref: Prefecture,
        @Column(name = "city_name", length = 12)
        var cityName: String?) {
}