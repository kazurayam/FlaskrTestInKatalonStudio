package flaskrpages.blog

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By as SeleniumBy

import com.kazurayam.ks.testobject.By
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

class IndexPage {

	static final String URL = 'http://127.0.0.1:80/'
	static final TestObject REGISTER_ANCHOR = By.xpath('//a[contains(text(), "Register")]')
	static final TestObject LOGIN_ANCHOR    = By.xpath('//a[contains(text(), "Log In")]')
	static final TestObject LOGOUT_ANCHOR   = By.xpath('//a[contains(text(), "Log Out")]')
	static final TestObject POSTS_HEADER    = By.xpath('//h1[contains(text(), "Posts")]')
	static final TestObject NEW_ANCHOR      = By.xpath('//a[contains(text(), "New")]')
	static final TestObject POSTS           = By.xpath('//article[@class="post"]')
	static final int TIMEOUT = 3

	/**
	 * @param index 1,2,3, ...
	 */
	static final TestObject POST_BY_INDEX(String index) {
		return By.xpath("//article[@class='post' and position()=${index}]")
	}

	static final TestObject POST_BY_POSTID(String id) {
		return By.xpath("//article[@class='post']/header/a[starts-with(@href, '/${id}')]/ancestor::article")
	}

	private static WebDriver browser = null

	IndexPage(WebDriver browser) {
		this.browser = browser
	}

	void load() {
		DriverFactory.changeWebDriver(browser)
		WebUI.navigateToUrl(URL)
	}

	void open_register_page() {
		DriverFactory.changeWebDriver(browser)
		WebUI.click(REGISTER_ANCHOR)
	}

	void open_login_page() {
		DriverFactory.changeWebDriver(browser)
		WebUI.click(LOGIN_ANCHOR)
	}

	Boolean posts_header_exists() {
		DriverFactory.changeWebDriver(browser)
		return WebUI.waitForElementPresent(POSTS_HEADER, TIMEOUT)
	}

	void open_create_post_page() {
		DriverFactory.changeWebDriver(browser)
		WebUI.click(NEW_ANCHOR)
	}

	int get_posts_count() {
		List<WebElement> posts = WebUI.findWebElements(POSTS, TIMEOUT)
	}

	void open_update_page_of_latest() {
		this.open_update_page_by_index(1)
	}

	void open_update_page_by_index(int index) {
		DriverFactory.changeWebDriver(browser)
		WebElement article = WebUI.findWebElement(POST_BY_INDEX(index), TIMEOUT)
		if (article != null) {
			this.open_update_page(article)
		} else {
			throw new IllegalArgumentException("no <article> found; index: ${index}")
		}
	}

	void open_update_pagee_by_postid(String postid) {
		DriverFactory.changeWebDriver(browser)
		WebElement article = WebUI.findWebElement(POST_BY_POSTID(postid))
		if (article != null) {
			this.open_update_page(article)
		} else {
			throw new IllegalArgumentException("no <article> found; postid: ${postid}")
		}
	}

	static void open_update_page(WebElement article) {
		Objects.requireNonNull(browser)
		// I could not translate "WebElement.findElement(SeleniumBy.xpath("..."))" to Katalon Studio's WebUI.* keywords
		WebElement anchor = article.findElement(SeleniumBy.xpath(
				"//a[contains(text(), 'Edit')]"))
		if (anchor != null) {
			anchor.click()
		} else {
			throw new IllegalArgumentException("no <a>Edit</a> found")
		}
	}

	Post get_post_latest() {
		return this.get_post_by_index(1)
	}

	Post get_post_by_index(int index) {
		DriverFactory.changeWebDriver(browser)
		WebElement article = WebUI.findWebElement(POST_BY_INDEX(index))
		if (article != null) {
			return new Post(article)
		} else {
			return null
		}
	}

	Post get_post_by_postid(String postid) {
		DriverFactory.changeWebDriver(browser)
		WebElement article = WebUI.findWebElement(POST_BY_POSTID(postid))
		if (article != null) {
			return new Post(article)
		} else {
			return null
		}
	}
}
