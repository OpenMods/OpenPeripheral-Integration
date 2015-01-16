package openperipheral.integration;

import openmods.conditions.ICondition;
import openmods.integration.IntegrationModule;

public abstract class CustomIntegrationModule extends IntegrationModule {

	public CustomIntegrationModule(ICondition condition) {
		super(condition);
	}

	@Override
	public String name() {
		return getId() + "(custom) CC integration module";
	}

	protected abstract String getId();
}
