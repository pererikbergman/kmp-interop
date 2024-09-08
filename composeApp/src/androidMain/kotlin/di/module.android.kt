package di

import com.rakangsoftware.interop.AndroidService
import com.rakangsoftware.interop.Service
import org.koin.dsl.module

actual val platformModule = module {
    single<Service> { AndroidService() }
}

