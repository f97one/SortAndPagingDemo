package nwt.formula97.webapps.sortandpagingdemo.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "prefecture")
data class Prefecture(
        @Id
        @Column(name = "pref_id", length = 2)
        var prefId: String,
        @Column(name = "pref_name", length = 10)
        var prefName: String?) {
}