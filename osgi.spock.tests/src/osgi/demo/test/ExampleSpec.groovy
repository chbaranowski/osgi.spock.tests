package osgi.demo.test

import java.util.function.Consumer

import org.knowhowlab.osgi.testing.utils.ServiceUtils
import org.osgi.framework.BundleContext
import org.osgi.framework.FrameworkUtil
import org.osgi.framework.ServiceRegistration

import osgi.echo.api.Echo
import spock.lang.*

class ExampleSpec extends Specification {
	
	@Shared BundleContext context = FrameworkUtil.getBundle(this.getClass()).bundleContext
	
	ServiceRegistration<Consumer<String>> registration
	
	Consumer<String> printerMock = Mock(Consumer)
	
	def setup() {
		registration = context.registerService(Consumer.class, printerMock , null)
	}
	
	def cleanup() {
		registration.unregister()
	}

	def sayHelloToEchoService() {
		given:
		Echo echo = ServiceUtils.getService(context, Echo)
		when:
		echo.print(msg)
		then:
		1 * printerMock.accept(msg)
		
		where:
		msg << ["Hello OSGi Service"]
	}
	
	def counterShouldBeHigherAfterCallingPrint() {
		given:
		Echo echo = ServiceUtils.getService(context, Echo)
		when:
		echo.print(msg)
		then:
		echo.count() > old(echo.count())
		
		where:
		msg << ["Hello OSGi Service"]
	}
		
}
