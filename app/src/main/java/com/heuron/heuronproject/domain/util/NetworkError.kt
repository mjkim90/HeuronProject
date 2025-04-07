package com.heuron.heuronproject.domain.util

sealed class NetworkError(val message: String) {
    data object NoInternet : NetworkError("인터넷 연결을 확인해주세요.")
    data object Timeout : NetworkError("요청 시간이 초과 되었습니다.")
    data object ServerNotUsed : NetworkError("서버에 연결할 수 없습니다.")
    data class Unknown(val cause: Throwable?) : NetworkError(cause?.message ?: "알 수 없는 오류가 발생 하였습니다.")
}