@file:Suppress("UNUSED")

import com.rakangsoftware.interop.Service
import di.platformModule

fun registerService(service: Service) {
     platformModule.single { service as Service }
}
