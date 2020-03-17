import { LocalizedValue } from '../../shared/model/core';
import { LocalizationService } from '../../shared/service/localization.service';
import { ImportConfiguration } from './io';

export const PRESENT: string = '5000-12-31'

export class TreeEntity {
	id: string;
	name: string;
	hasChildren: boolean;
}

export class Term {
	code: string;
	label: LocalizedValue;
	description: LocalizedValue;

	constructor(code: string, label: LocalizedValue, description: LocalizedValue) {
		this.code = code;
		this.label = label;
		this.description = description;
	}
	children: Term[] = [];

	addChild(term: Term) {
		this.children.push(term);
	}
}

export class GeoObject {
	type: string;
	geometry: any;
	properties: {
		uid: string,
		code: string,
		displayLabel: LocalizedValue,
		type: string,
		status: string[],
		sequence: string
		createDate: string,
		lastUpdateDate: string,
	};
}

export class GeoObjectType {
	code: string;
	label: LocalizedValue;
	description: LocalizedValue;
	geometryType: string;
	isLeaf: boolean;
	isGeometryEditable: boolean;
	attributes: Array<Attribute | AttributeTerm | AttributeDecimal> = [];
}

// export class Attribute {

//   name: string;
//   type: string;
//   label: string;
//   description: string;
//   isDefault: boolean;
// }

// export class AttributeTerm extends Attribute {
//     descendants: Attribute[];
//     rootTerm: string;
// }

export class GeoObjectOverTime {

	geoObjectType: GeoObjectType;

	attributes: any;

	public constructor(geoObjectType: GeoObjectType, attributes: any) {
		this.geoObjectType = geoObjectType;
		this.attributes = attributes;
	}

	public getVotAtDate(date: Date, attrCode: string, lService: LocalizationService) {
		let retVot = { startDate: date, endDate: null, value: null };

		const time = date.getTime();

		for (let i = 0; i < this.geoObjectType.attributes.length; ++i) {
			let attr = this.geoObjectType.attributes[i];

			if (attr.code === attrCode) {
				if (attr.type === 'local') {
					retVot.value = lService.create();
				}

				if (attr.isChangeOverTime) {
					let values = this.attributes[attr.code].values;

					values.forEach(vot => {

						const startDate = Date.parse(vot.startDate);
						const endDate = Date.parse(vot.endDate);

						if (time >= startDate && time <= endDate) {

							if (attr.type === 'local') {
								retVot.value = JSON.parse(JSON.stringify(vot.value));
							}
							else if (attr.type === 'term' && vot.value != null && Array.isArray(vot.value) && vot.value.length > 0) {
								retVot.value = vot.value[0];
							}
							else {
								retVot.value = vot.value;
							}
						}
					});
				}
				else {
					retVot.value = this.attributes[attr.code];
				}

				break;
			}
		}

		return retVot;
	}
}

export class ValueOverTime {
	startDate: string;
	endDate: string;
	value: any;
	removable?: boolean;
}

export class Attribute {
	code: string;
	type: string;
	label: LocalizedValue;
	description: LocalizedValue;
	isDefault: boolean;
	required: boolean;
	unique: boolean;
	isChangeOverTime?: boolean;

	constructor(code: string, type: string, label: LocalizedValue, description: LocalizedValue, isDefault: boolean, required: boolean, unique: boolean, isChangeOverTime: boolean) {

		this.code = code;
		this.type = type;
		this.label = label;
		this.description = description;
		this.isDefault = isDefault;
		this.required = required;
		this.unique = unique;
		this.isChangeOverTime = isChangeOverTime;
	}

}

export class AttributeTerm extends Attribute {
	//descendants: Attribute[];

	constructor(code: string, type: string, label: LocalizedValue, description: LocalizedValue, isDefault: boolean, required: boolean, unique: boolean, isChange: boolean) {
		super(code, type, label, description, isDefault, required, unique, isChange);
	}

	rootTerm: Term = new Term(null, null, null);

	termOptions: Term[] = [];

	setRootTerm(term: Term) {
		this.rootTerm = term;
	}
}

export class AttributeDecimal extends Attribute {
	precision: number = 32;
	scale: number = 8;

	constructor(code: string, type: string, label: LocalizedValue, description: LocalizedValue, isDefault: boolean, required: boolean, unique: boolean, isChange: boolean) {
		super(code, type, label, description, isDefault, required, unique, isChange);
	}
}

export enum GeoObjectTypeModalStates {
	"manageAttributes" = "MANAGE-ATTRIBUTES",
	"editAttribute" = "EDIT-ATTRIBUTE",
	"defineAttribute" = "DEFINE-ATTRIBUTE",
	"manageTermOption" = "MANAGE-TERM-OPTION",
	"editTermOption" = "EDIT-TERM-OPTION",
	"manageGeoObjectType" = "MANAGE-GEO-OBJECT-TYPE"
}

export class TreeNode {
	geoObject: GeoObject;
	hierarchyType: string;
}

export class ChildTreeNode extends TreeNode {
	children: ChildTreeNode[];
}

export class ParentTreeNode extends TreeNode {
	parents: ParentTreeNode[];
}

export class ManageGeoObjectTypeModalState {
	state: string;
	attribute: any;
	termOption: any;
}

export class PaginationPage {
	pageNumber: number;
	count: number;
	pageSize: number;
	results: any[];
}

export class ScheduledJob {
	fileName: string;
	historyId: string;
	stage: string;
	status: string;
	author: string;
	createDate: string;
	lastUpdateDate: string;
	workProgress: number;
	workTotal: number;
	startDate: string;
	endDate: string;
	importedRecords: number;
	configuration: ImportConfiguration;
	importErrors: PaginationPage;
	problems: PaginationPage;
}

export class ScheduledJobOverview extends ScheduledJob {
	stepConfig: StepConfig;
}

// export class ScheduledJobDetail extends ScheduledJob {
//     // failedRowCount: number;
//     importErrors: PaginationPage
// }

export class Conflict {
	exception: ConflictError;
	object: ConflictObject;
	objectType: string;
	selected?: boolean;
}

export class ConflictError {
	attributes: ConflictErrorAttribute[];
	type: string;
	message: string;
}

export class ConflictErrorAttribute {
	value: string;
	key: string;
}

export class ConflictObject {
	geoObject: GeoObjectOverTime;
	parents: HierarchyOverTime[];
}

export class StepConfig {
	steps: Step[];
}

export class Step {
	label: string;
	complete: boolean;
	enabled: boolean;
}

export class MasterList {
	oid: string;
	typeCode: string;
	displayLabel: LocalizedValue;
	code: string;
	representativityDate: Date;
	publishDate: Date;
	listAbstract: string;
	process: string;
	progress: string;
	accessConstraints: string;
	useConstraints: string;
	acknowledgements: string;
	disclaimer: string;
	contactName: string;
	organization: string;
	telephoneNumber: string;
	email: string;
	hierarchies: { label: string, code: string, parents: { label: string, code: string }[] }[];
	leaf: boolean;
	frequency: string;
	isMaster: boolean;
	visibility: string;
	admin: boolean;
	versions?: MasterListVersion[]
}

export class MasterListVersion {
	displayLabel: string;
	oid: string;
	typeCode: string;
	leaf: boolean;
	masterlist: string;
	forDate: string;
	createDate: string;
	publishDate: string;
	attributes: any[];
	isGeometryEditable: boolean;
	locales?: string[];
	shapefile?: boolean;
}

export class HierarchyOverTime {
	code: string;
	label: string;
	types: {
		code: string;
		label: string;
	}[];
	entries: {
		startDate: string;
		endDate: string;
		parents: { [k: string]: { text: string; geoObject: GeoObject } };
	}[];
}

export enum ImportStrategy {
	"NEW_AND_UPDATE" = "NEW_AND_UPDATE",
	"NEW_ONLY" = "NEW_ONLY",
	"UPDATE_ONLY" = "UPDATE_ONLY"
}

export class MasterListByOrg {
	oid: string;
	label: string;
	lists: { label: string, oid: string, createDate: string, lastUpdateDate: string, admin: boolean, isMaster: boolean }[];
}


