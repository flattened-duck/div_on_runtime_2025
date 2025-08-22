import SwiftUI
import DivKit

struct PlaygroundView: View {
  @ObservedObject
  var model: PlaygroundModel
  
  let divKitComponents: DivKitComponents
  let cardId = DivCardID.init(rawValue: "playground_card")

    var body: some View {
    VStack {
      Text("DivKit Playground")
        .font(.title)
        .padding()
      
      // Создаем View для отрисовки DivKit
      DivHostingView(
        divkitComponents: divKitComponents,
        source: DivViewSource(
          kind: .data(model.jsonData ?? Data()),
          cardId: cardId
        ),
        shouldResetPreviousCardData: true
      )
    }.onAppear() {
      // Загружаем json в первый раз
      model.loadJson()
    }
  }
}
