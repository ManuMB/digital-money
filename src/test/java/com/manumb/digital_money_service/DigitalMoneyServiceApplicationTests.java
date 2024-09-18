package com.manumb.digital_money_service;

import com.manumb.digital_money_service.tests.Sprint1Tests;
import com.manumb.digital_money_service.tests.Sprint2Tests;
import com.manumb.digital_money_service.tests.Sprint3Tests;
import com.manumb.digital_money_service.tests.Sprint4Tests;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@Suite
@SelectClasses({Sprint1Tests.class, Sprint2Tests.class, Sprint3Tests.class, Sprint4Tests.class})
class DigitalMoneyServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
