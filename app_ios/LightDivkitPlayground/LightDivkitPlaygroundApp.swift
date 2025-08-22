import SwiftUI
import DivKit

@main
struct LightDivkitPlaygroundApp: App {
    
  let divKitComponents: DivKitComponents
  let model: PlaygroundModel
  
  init() {
    DivKitLogger.isEnabled = true
    
    // Создаем модель для загрузки json
    model = PlaygroundModel()
    
    // Создаем обработчик действия
    let urlHandler = PlaygroundUrlHandler(model: model)
    
    // Создаем DivKitComponents, контейнер для зависимостей DivView и передаем наш urlHandler
    divKitComponents = DivKitComponents(
      urlHandler: urlHandler
    )
  } 
    
  var body: some Scene {
      WindowGroup {
        PlaygroundView(
          model: model, 
          divKitComponents: divKitComponents
        )
      }
  }
}
