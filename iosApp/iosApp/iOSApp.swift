import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        PlatformSetupKt.registerService(service: IOSService())
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
