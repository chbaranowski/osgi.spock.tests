package osgi.echo.ds;

import java.util.function.Consumer;

import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;
import osgi.echo.api.Echo;

@Component
public class EchoComponent implements Echo {

	Consumer<String> printer;
	
	long counter;
	
	@Override
	public void print(String msg) {
		counter ++;
		printer.accept(msg);
	}

	@Reference
	public void setPrinter(Consumer<String> printer) {
		this.printer = printer;
	}

	@Override
	public long count() {
		return counter;
	}

}
