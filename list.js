
/**
* 現在の順序を保持します。
*/
var currentOrderBy = undefined;

// 最新の一覧の取得
function updateList(key)
{
	var msec = (new Date()).getTime();
	new Ajax.Request("list_json.action", {
		method: "post",
		parameters: "cache="+msec + "&order_by=" + encodeURIComponent(key),
		onSuccess:function(httpObj){
			try{
				var obj = httpObj.responseText.evalJSON();
				
				var updater = new Updater(obj.currentUser, obj.items, obj.order_by);
				updater.update();
				
			}catch(e) {
				alert(e);
			}
		},
		onFailure:function(httpObj){
			alert("HTTP読み込みエラー");
		}
	});
}

/**
* マウスオーバーを取得します。
*/
function mouseOver(key)
{
	var elem = getSpanElement(key);
	elem.style.color = "#ffbbbb";
}

/**
* マウスが外れたことを取得します。
*/
function mouseOut(key)
{
	var elem = getSpanElement(key);
	elem.style.color = "#ffffff";
}

/**
* span要素を取得します。
*/
function getSpanElement(key)
{
	var id = key;
	var dir = "a";
	if(key.startsWith("!")) {
		id = key.substring(1);
		dir = "d";
	}
	if(id.endsWith("Date")) {
		id = id.substring(0, id.length - "Date".length);
	}
	
	return $("sort_" + id + "_" + dir);
}


/**
* テーブル更新を担当するクラス。
* @param currentUser
* @param items
* @param orderBy
*/
function Updater(currentUser, items, orderBy)
{
	/**
	* アイテムの配列を保持します。
	*/
	this._items = items;
	
	/**
	* 現在のユーザを保持します。
	*/
	this._currentUser = currentUser;
	
	/**
	* 順序を保持します。
	*/
	this._orderBy = orderBy;
	if(orderBy == undefined) {
		this._orderBy = currentOrderBy;
	}else{
		currentOrderBy = orderBy;
	}
	
	/**
	* テーブル要素を保持します。
	*/
	this._table = $('item_list');
	
	/**
	* 更新します。
	*/
	this.update = function()
	{
		var trCount = this._getTRElementCount();
		
		if(trCount - 1 != this._items.length) {
			var option = "";
			if(this._orderBy != undefined) {
				option = "?order_by=" + encodeURIComponent(this._orderBy);
			}
			window.location.href = "list.action" + option;
			return;
		}
		
		var currentDate = this._getCurrentDate();
		for(var i = 0; i < this._items.length; i ++) {
			var item = this._items[i];
			
			var nameText = this._getText(i,0);
			if(nameText != undefined) {
				nameText.data = item.name;
			}
			var userText = this._getText(i,1);
			if(userText != undefined) {
				userText.data = item.user.name;
			}
			var expireText = this._getText(i,2);
			var tdStyle = "";
			var colorStyle = "#000000";
			var bgColorStyle = "#bbbbff";
			var expireString = this._getDate(item.expireDate);
			var expireDate = this._parseDate(expireString);
			if(expireText != undefined) {
				expireText.data = expireString;
			}
			var finishedString = undefined;
			var finishedText = this._getText(i,3);
			if(finishedText != undefined) {
				if(item.finishedDate == null) {
					finishedText.data = "未";
					if(currentDate.getTime() > expireDate.getTime()) {
						colorStyle = "#ff0000";
					}
					if(this._currentUser.id == item.user.id) {
						bgColorStyle = "#ffbbbb";
					}
				}else{
					finishedString = this._getDate(item.finishedDate);
					finishedText.data = finishedString;
					bgColorStyle = "#cccccc";
				}
			}
			
			for(var j = 4; j < 7; j ++) {
				var hiddenElem = this._getHiddenElement(i, j);
				hiddenElem.setAttribute("value", item.id);
			}
			
			var submitElem = this._getSubmitElement(i, 4);
			if(item.finishedDate == null) {
				submitElem.setAttribute("value", "完了");
			}else{
				submitElem.setAttribute("value", "未完了");
			}
			
			for(var j = 0; j < 7; j ++) {
				var divElem = this._getDIVElement(i, j);
				divElem.style.color = colorStyle;
				var tdElem = this._getTDElement(i, j);
				tdElem.style.backgroundColor = bgColorStyle;
			}
		}
	};
	
	/**
	* 現在の日付を取得します。
	*/
	this._getCurrentDate = function()
	{
		var date = new Date();
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		return date;
	};
	
	/**
	* 日時を解析する。
	*/
	this._parseDate = function(text)
	{
		var yearIndex = text.indexOf('-');
		if(yearIndex < 0) {
			return undefined;
		}
		var monthIndex = text.indexOf('-', yearIndex + 1);
		if(monthIndex < 0) {
			return undefined;
		}
		
		var year = parseInt(text.substring(0, yearIndex));
		var month = parseInt(text.substring(yearIndex + 1, monthIndex));
		var day = parseInt(text.substring(monthIndex + 1));
		
		return new Date(year, month - 1, day);
	};
	
	/**
	* TR要素を取得します。
	*/
	this._getTBODYElement = function()
	{
		// 
		var childNodes = this._table.childNodes;
		
		for(var i = 0; i < childNodes.length; i ++) {
			var node = childNodes.item(i);
			
			if(node.tagName != undefined) {
				if(node.tagName.toUpperCase() == "TBODY") {
					return node;
				}
			}
		}
		
		return this._table;
	};
	
	/**
	* TR要素を取得します。
	*/
	this._getTRElement = function(index)
	{
		// 
		var absoluteIndex = index + 1;
		var childNodes = this._getTBODYElement().childNodes;
		var count = 0;
		
		for(var i = 0; i < childNodes.length; i ++) {
			var node = childNodes.item(i);
			
			if(node.tagName != undefined) {
				if(node.tagName.toUpperCase() == "TR") {
					if(count == absoluteIndex) {
						return node;
					}
					count ++;
				}
			}
		}
		
		return undefined;
	};
	
	/**
	* TR要素の数を取得します。
	*/
	this._getTRElementCount = function()
	{
		// 
		var childNodes = this._getTBODYElement().childNodes;
		var count = 0;
		
		for(var i = 0; i < childNodes.length; i ++) {
			var node = childNodes.item(i);
			
			if(node.tagName != undefined) {
				if(node.tagName.toUpperCase() == "TR") {
					count ++;
				}
			}
		}
		
		return count;
	};
	
	/**
	* TD要素を取得します。
	*/
	this._getTDElement = function(rowIndex, colIndex)
	{
		// 
		var trElem = this._getTRElement(rowIndex);
		if(trElem == undefined) {
			return undefined;
		}
		var childNodes = trElem.childNodes;
		var count = 0;
		
		for(var i = 0; i < childNodes.length; i ++) {
			var node = childNodes.item(i);
			
			if(node.tagName != undefined) {
				if(node.tagName.toUpperCase() == "TD") {
					if(count == colIndex) {
						return node;
					}
					count ++;
				}
			}
		}
		
		return undefined;
	};
	
	/**
	* div要素を取得します。
	*/
	this._getDIVElement = function(rowIndex, colIndex)
	{
		var tdElem = this._getTDElement(rowIndex, colIndex);
		if(tdElem == undefined) {
			return undefined;
		}
		
		var childNodes = tdElem.childNodes;
		
		for(var i = 0; i < childNodes.length; i ++) {
			var node = childNodes.item(i);
			
			if(node.tagName != undefined) {
				if(node.tagName.toUpperCase() == "DIV") {
					return node;
				}
			}
		}
		return undefined;
	};
	
	/**
	* hidden要素を取得します。
	*/
	this._getHiddenElement = function(rowIndex, colIndex)
	{
		var tdElem = this._getTDElement(rowIndex, colIndex);
		if(tdElem == undefined) {
			return undefined;
		}
		
		return this._findInputElement("hidden", "item_id", tdElem);
	}
	
	/**
	* submit要素を取得します。
	*/
	this._getSubmitElement = function(rowIndex, colIndex)
	{
		var tdElem = this._getTDElement(rowIndex, colIndex);
		if(tdElem == undefined) {
			return undefined;
		}
		
		return this._findInputElement("submit", undefined, tdElem);
	}
	
	/**
	* input要素を検索します。
	*/
	this._findInputElement = function(typeName, name, targetElem)
	{
		if(targetElem.hasChildNodes() == false) {
			return undefined;
		}
		var childNodes = targetElem.childNodes;
		
		for(var i = 0; i < childNodes.length; i ++) {
			var node = childNodes.item(i);
			
			if(this._checkInputElement(typeName, name, node)) {
				return node;
			}
			
			var hiddenElem = this._findInputElement(typeName, name, node);
			if(hiddenElem != undefined) {
				return hiddenElem;
			}
		}
		return undefined;
	};
	
	/**
	* input要素をチェックします。
	*/
	this._checkInputElement = function(typeName, name, node)
	{
		if(node.tagName == undefined) {
			return false;
		}
		if(node.tagName.toUpperCase() != "INPUT") {
			return false;
		}
		var typeValue = node.getAttribute("type");
		if(typeValue == "" || typeValue == undefined) {
			typeValue = node.getAttribute("TYPE");
		}
		if(typeValue == undefined) {
			return false;
		}
		if(typeValue.toUpperCase() != typeName.toUpperCase()) {
			return false;
		}
		
		if(name == undefined) {
			return true;
		}
		var nameAttr = node.getAttribute("name");
		if(nameAttr == "" || nameAttr == undefined) {
			nameAttr = node.getAttribute("NAME");
		}
		if(nameAttr != name) {
			return false;
		}
		return true;
	};
	
	/**
	* 文字列要素を取得します。
	*/
	this._getText = function(rowIndex, colIndex)
	{
		var divElem = this._getDIVElement(rowIndex, colIndex);
		if(divElem == undefined) {
			return undefined;
		}
		
		var childNodes = divElem.childNodes;
		
		for(var i = 0; i < childNodes.length; i ++) {
			var node = childNodes.item(i);
			
			if(node.data != undefined) {
				return node;
			}
		}
		return undefined;
	};
	
	/**
	* 日付を取得します。
	*/
	this._getDate = function(timeStr)
	{
		var index = timeStr.indexOf("T");
		if(index < 0) {
			return timeStr;
		}
		return timeStr.substring(0, index);
	};
	
}


