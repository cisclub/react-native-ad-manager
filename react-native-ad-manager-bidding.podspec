require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-ad-manager-bidding"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-ad-manager-bidding
                   DESC
  s.homepage     = "https://github.com/cisclub/react-native-ad-manager-bidding"
  s.license      = "MIT"
  s.license      = package['license']
  s.authors      = { "cisclub" => "" }
  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.com/cisclub/react-native-ad-manager-bidding.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,m,swift}"
  s.requires_arc = true

  s.dependency "React"
  s.dependency 'Google-Mobile-Ads-SDK', '7.68.0'
  s.dependency 'GoogleMobileAdsMediationFacebook'
  s.dependency 'CriteoPublisherSdk'
end
