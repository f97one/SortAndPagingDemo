package nwt.formula97.webapps.sortandpagingdemo.domain

import org.springframework.data.domain.Page

class Pagination<T>(page: Page<T>) {
    val first: Boolean = page.isFirst
    val last: Boolean = page.isLast
    val size: Int = page.size
    val number: Int = page.number
    val numberOfElements: Int = page.numberOfElements
    val totalElements: Long = page.totalElements
    val totalPages: Int = page.totalPages
}