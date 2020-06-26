/**
 * 
 */

class AccountType {
	constructor() {
		this.compoundMonths;
		this.interestRate;
		this.monthlyFee;
		this.permission;
		this.permissionId;
		this.type;
		this.typeId;
	}

	get getCompundMonths()
	{
		return this.compoundMonths;
	}
	set setCompoundMonths(months)
	{
		this.compoundMonths = months;
	}
	
	get getInterestRate()
	{
		return this.interestRate;
	}
	set setInterestRate(rate)
	{
		this.interestRate = rate;
	}
	
	get getMonthlyFee()
	{
		return this.monthlyFee;
	}
	set setMonthlyFee(fee)
	{
		this.monthlyFee = fee;
	}
	
	get getPermission()
	{
		return this.permission;
	}
	set setPermission(Permission )
	{
		this.permission(Permission) = permission;
	}
}

class Permission {
	constructor()
	{
		this.foo;
	}
}
