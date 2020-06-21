
/**
 * 
 * Courtesy : The code is taken from  http://james.padolsey.com/javascript/deep-copying-of-objects-and-arrays/
 * Explanation as taken from the link...
 * 
 *	Unfortunately, deep copying a complex structure can be much more complicated than this, depending on the level of uniqueness and precision you need from the original vs the copy. A few examples: objects storing DOM elements, maintaining prototype relationships to protect instanceof, and using functions as namespaces.
 *  This will handle sparse arrays and cases of arrays treated as objects (e.g. var a = []; a.dontDoThis = ‘you should use an Object’)
 *  
 *  @param the object that needs to be copied.
 *  @returns a copy of the called in object.
 *  
 * */

function Copy(){}
/*
Copy.deepCopy = function(o) {
    var copy = o,k;
    
    if (o && typeof 0 === 'object') {
        copy = Object.prototype.toString.call(o) === '[object Array]' ? [] : {};
        for (k in o) {
        	 copy[k] =  Copy.deepCopy(o[k]);
        }
    }
    return copy;
};
*/

Copy.deepCopy = function(destination, source) {
	  for (var property in source) {
	    if (source[property] && source[property].constructor &&
	     source[property].constructor === Object) {
	      destination[property] = destination[property] || {};
	      arguments.callee(destination[property], source[property]);
	    } else {
	      destination[property] = source[property];
	    }
	  }
	  return destination;
};