if (Ext.data.Store) {
	Ext.apply(Ext.data.Store.prototype, {
		sortData : function(f, direction) {
			direction = direction || 'ASC';
			var st = this.fields.get(f).sortType;
			var fn = function(r1, r2) {
				var v1 = st(r1.data[f]), v2 = st(r2.data[f]);
				if (typeof (v1) == "string")
					return v1.localeCompare(v2);
				else
					return v1 > v2 ? 1 : (v1 < v2 ? -1 : 0);
			};
			this.data.sort(direction, fn);
			if (this.snapshot && this.snapshot != this.data) {
				this.snapshot.sort(direction, fn);
			}
		}
	})
}

Ext.apply(Ext.form.VTypes, {
	
	daterange : function(val, field) {
		var date = field.parseDate(val);

		if (!date) {
			return;
		}
		if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
			var start = Ext.getCmp(field.startDateField);
			start.setMaxValue(date);
			start.validate();
			this.dateRangeMax = date;
		} else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
			var end = Ext.getCmp(field.endDateField);
			end.setMinValue(date);
			end.validate();
			this.dateRangeMin = date;
		}
		/*
		 * Always return true since we're only using this vtype to set the
		 * min/max allowed values (these are tested for after the vtype test)
		 */
		return true;
	},

	password : function(val, field) {
		if (field.initialPassField) {
			var pwd = Ext.getCmp(field.initialPassField);
			return (val == pwd.getValue());
		}
		return true;
	},
	passwordText : '两次输入的密码不同。'

});
