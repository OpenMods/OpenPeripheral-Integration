package openperipheral.integration.cofh.tileentity;

import cofh.api.tileentity.ISecurable;

import com.google.common.base.Preconditions;

public class SecurityUtils {

	public static boolean canModify(Object o) {
		return (o instanceof ISecurable)? ((ISecurable)o).getAccess().isPublic() : true;
	}

	public static void checkAccess(Object o) {
		Preconditions.checkArgument(canModify(o), "Access restricted");
	}

}
