package openperipheral.integration.thaumcraft;

import net.minecraftforge.common.util.ForgeDirection;
import openperipheral.api.adapter.Asynchronous;
import openperipheral.api.adapter.IPeripheralAdapter;
import openperipheral.api.adapter.method.Arg;
import openperipheral.api.adapter.method.ReturnType;
import openperipheral.api.adapter.method.ScriptCallable;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IEssentiaTransport;

@Asynchronous
public class AdapterEssentiaTransport implements IPeripheralAdapter {

	@Override
	public Class<?> getTargetClass() {
		return IEssentiaTransport.class;
	}

	@Override
	public String getSourceId() {
		return "thaumcraft_essentia_transport";
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Returns the amount of suction in the tube")
	public int getSuctionAmount(IEssentiaTransport pipe,
			@Arg(description = "Direction suction coming from", name = "direction") ForgeDirection direction) {
		return pipe.getSuctionAmount(direction);
	}

	@ScriptCallable(returnTypes = ReturnType.STRING, description = "Returns the type of essentia wished in the tube")
	public String getSuctionType(IEssentiaTransport pipe,
			@Arg(description = "Direction suction coming from", name = "direction") ForgeDirection direction) {
		Aspect asp = pipe.getSuctionType(direction);
		return (asp != null)? asp.getTag() : "";
	}

	@ScriptCallable(returnTypes = ReturnType.NUMBER, description = "Returns the amount of essentia in the tube")
	public int getEssentiaAmount(IEssentiaTransport pipe,
			@Arg(description = "Direction suction coming from", name = "direction") ForgeDirection direction) {
		return pipe.getEssentiaAmount(direction);
	}

	@ScriptCallable(returnTypes = ReturnType.STRING, description = "Returns the type of essentia in the tube")
	public String getEssentiaType(IEssentiaTransport pipe,
			@Arg(description = "Direction suction coming from", name = "direction") ForgeDirection direction) {
		Aspect asp = pipe.getEssentiaType(direction);
		return (asp != null)? asp.getTag() : "";
	}

}
