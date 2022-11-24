dependencies {
	implementation(project(":coupon-redis"))
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("com.mysql:mysql-connector-j")
	testRuntimeOnly("com.h2database:h2")
}