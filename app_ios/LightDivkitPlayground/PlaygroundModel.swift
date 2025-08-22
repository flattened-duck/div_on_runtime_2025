import Foundation
import DivKit

class PlaygroundModel: ObservableObject {
  @Published var jsonData: Data?
  
  func loadJson() {
    Task {
      do {
        let request = try makeRequest()
        let (data, _) = try await URLSession.shared.data(for: request)
        await MainActor.run {
          jsonData = data
        }
      } catch {
        DivKitLogger.error("Error on loading json: \(error.localizedDescription)")
      }
    }
  }
  
  private func makeRequest() throws -> URLRequest {
    var urlComponents = URLComponents()
    urlComponents.scheme = "http"
    urlComponents.host = "localhost"
    urlComponents.port = 8080
    urlComponents.path = "/demo"
    urlComponents.queryItems = [
      URLQueryItem(name: "username", value: "div_user")
    ]

    guard let url = urlComponents.url else {
      // Обработка ошибки - URL не может быть создан
      throw URLError(.badURL)
    }

    var request = URLRequest(url: url)
    request.addValue("ru", forHTTPHeaderField: "Accept-Language")
    
    return request
  }
}


