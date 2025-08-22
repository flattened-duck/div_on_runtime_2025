import DivKit
import Foundation

class PlaygroundUrlHandler: DivUrlHandler {
  let model: PlaygroundModel

  init(model: PlaygroundModel) {
    self.model = model
  }

  // Когда пользователь нажимает на action и DivKit не смог обработать действие, то попадаем в этот метод.
  func handle(_ actionUrl: URL, info: DivActionInfo, sender: AnyObject?) {
    guard actionUrl.scheme == "sample-action" else { return }
    
    switch actionUrl.host {
    // Обрабатываем кастомные действия
    case "update":
      model.loadJson()
    default:
      DivKitLogger.error("Unknown action: \(actionUrl.absoluteString)")
    }
  }
}
