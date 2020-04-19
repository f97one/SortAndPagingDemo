package nwt.formula97.webapps.sortandpagingdemo.repository

import nwt.formula97.webapps.sortandpagingdemo.domain.Prefecture
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PrefectureRepository: JpaRepository<Prefecture, String> {
}