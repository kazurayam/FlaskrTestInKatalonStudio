import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

String chrome_executable_path = DriverFactory.getChromeDriverPath()
System.setProperty('webdriver.chrome.driver', chrome_executable_path)

WebDriver browser = new ChromeDriver()
browser.navigate().to('http://127.0.0.1/')

// i can let WebUI.xxx keywords know the WebDriver instance created by my script
DriverFactory.changeWebDriver(browser)

String windowTitle = WebUI.getWindowTitle()
assert "Posts - Flaskr" == windowTitle