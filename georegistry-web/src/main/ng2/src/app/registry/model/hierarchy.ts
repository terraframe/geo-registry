import { LocalizedValue } from "@shared/model/core";

export class HierarchyType {
	code: string;
	description: LocalizedValue;
	label: LocalizedValue;
	rootGeoObjectTypes: HierarchyNode[];
	organizationCode: string;
	progress?: string;
	acknowledgement?: string;
	disclaimer?: string;
	contact?: string;
	phoneNumber?: string;
	email?: string;
	accessConstraints?: string;
	useConstraints?: string;
}

export class Hierarchy {
	id: string;
	label: string;
}

export class HierarchyNode {
	geoObjectType: string;
	children: HierarchyNode[];
	label: string;
	inheritedHierarchyCode: string;
}

export class HierarchyGroupedTypeView {
  code: string;
  label: string;
  orgCode: string;
  types: [{code: string, label: string, orgCode: string, permissions: [string]}];
}
