/*
 * Isomorphic SmartClient
 * Version v8.2p_2012-06-03 (2012-06-03)
 * Copyright(c) 1998 and beyond Isomorphic Software, Inc. All rights reserved.
 * "SmartClient" is a trademark of Isomorphic Software, Inc.
 *
 * licensing@smartclient.com
 *
 * http://smartclient.com/license
 */

if (window.isc && window.isc.module_Core && !window.isc.module_Drawing) {
    isc.module_Drawing = 1;
    isc._moduleStart = isc._Drawing_start = (isc.timestamp ? isc.timestamp() : new Date().getTime());
    if (isc._moduleEnd && (!isc.Log || (isc.Log && isc.Log.logIsDebugEnabled('loadTime')))) {
        isc._pTM = {message: 'Drawing load/parse time: ' + (isc._moduleStart - isc._moduleEnd) + 'ms', category: 'loadTime'};
        if (isc.Log && isc.Log.logDebug)
            isc.Log.logDebug(isc._pTM.message, 'loadTime')
        else if (isc._preLog)
            isc._preLog[isc._preLog.length] = isc._pTM
        else
            isc._preLog = [isc._pTM]
    }
    isc.definingFramework = true;
    isc.Browser.hasCANVAS = isc.Browser.geckoVersion >= 20051107 || isc.Browser.safariVersion >= 181 || ((isc.Browser.isIE && isc.Browser.version >= 9) && (typeof(document.createElement('canvas').getContext) === 'function'));
    isc.Browser.hasSVG = isc.Browser.geckoVersion >= 20051107;
    isc.Browser.hasVML = isc.Browser.isIE && isc.Browser.version >= 5;
    isc.Browser.defaultDrawingType = isc.Browser.hasCANVAS ? "bitmap" : isc.Browser.hasVML ? "vml" : "none";
    isc.defineClass("DrawPane", "Canvas");
    isc.A = isc.DrawPane.getPrototype();
    isc.B = isc._allFuncs;
    isc.C = isc.B._maxIndex;
    isc.D = isc._funcClasses;
    isc.D[isc.C] = isc.A.Class;
    isc.A.drawingType = isc.Browser.defaultDrawingType;
    isc.A.canDrag = false;
    isc.A.cursor = "all-scroll";
    isc.A.isMouseTransparent = true;
    isc.A.rotation = 0;
    isc.A.zoomLevel = 1;
    isc.A.translate = null;
    isc.A.gradients = {};
    isc.B.push(isc.A.createQuadTree = function isc_DrawPane_createQuadTree() {
        this.quadTree = isc.QuadTree.create({depth: 0, maxDepth: 50, maxChildren: 1});
        this.quadTree.bounds = {x: 0, y: 0, width: this.getInnerContentWidth(), height: this.getInnerContentHeight()};
        this.quadTree.root = isc.QuadTreeNode.create({depth: 0, maxDepth: 8, maxChildren: 4});
        this.quadTree.root.bounds = this.quadTree.bounds
    }
    , isc.A.updateQuadTree = function isc_DrawPane_updateQuadTree(_1) {
        this.quadTree.update(_1.item)
    }
    , isc.A.initWidget = function isc_DrawPane_initWidget() {
        this.drawItems = this.drawItems || [];
        this.canvasItems = this.canvasItems || [];
        this.createQuadTree();
        for (var i = 0; i < this.drawItems.length; i++) {
            this.addDrawItem(this.drawItems[i])
        }
        for (var i = 0; i < this.canvasItems.length; i++) {
            this.addCanvasItem(this.canvasItems[i])
        }
        this.redrawOnResize = (this.drawingType == "bitmap");
        this.$43z = this.getInnerContentWidth();
        this.$430 = this.getInnerContentHeight();
        this.$431 = 0;
        this.$432 = 0;
        this.$433 = this.$43z;
        this.$434 = this.$430;
        if (isc.Browser.isIE && this.drawingType == "vml") {
            document.createStyleSheet().addRule(".rvml", "behavior:url(#default#VML)");
            try {
                !document.namespaces.rvml && document.namespaces.add("rvml", "urn:schemas-microsoft-com:vml");
                this.startTagVML = function(_2) {
                    return'<rvml:' + _2 + ' class="rvml" '
                };
                this.endTagVML = function(_2) {
                    return'</rvml:' + _2 + '>'
                }
            } catch (e) {
                this.startTagVML = function(_2) {
                    return'<' + _2 + ' xmlns="urn:schemas-microsoft.com:vml" class="rvml" '
                };
                this.endTagVML = function(_2) {
                    return'</rvml:' + _2 + '>'
                }
            }
        }
    }
    , isc.A.normalize = function isc_DrawPane_normalize(_1, _2) {
        if (this.zoomLevel != 1 || this.rotation != 0) {
            var _3 = -this.rotation * Math.PI / 180.0;
            var _4 = _1 - this.width / 2;
            var _5 = _2 - this.height / 2;
            var _6 = _4 * Math.cos(_3) - _5 * Math.sin(_3);
            var _7 = _4 * Math.sin(_3) + _5 * Math.cos(_3);
            _6 /= this.zoomLevel;
            _7 /= this.zoomLevel;
            _6 += this.width / 2;
            _7 += this.height / 2;
            return{x: _6, y: _7}
        }
        return{x: _1, y: _2}
    }
    , isc.A.getDrawItem = function isc_DrawPane_getDrawItem(_1, _2) {
        _1 -= this.getPageLeft();
        _2 -= this.getPageTop();
        var _3 = this.normalize(_1, _2);
        _1 = _3.x;
        _2 = _3.y;
        var _4 = this.quadTree.retrieve({x: _1, y: _2});
        var _5 = _4 ? _4.length : 0;
        if (_5) {
            for (var i = 0; i < _5; ++i) {
                var _7 = _4[i].item || _4[i];
                var _8 = !_7.shape.hidden && _7.shape.isPointInPath(_1, _2);
                if (_8) {
                    return _7.shape
                }
            }
        }
        return this
    }
    , isc.A.getEventTarget = function isc_DrawPane_getEventTarget(_1) {
        switch (_1.eventType) {
            case'mouseUp':
            case'mouseDown':
            case'mouseMove':
            case'mouseOut':
            case'mouseOver':
            case'click':
                return this.getDrawItem(_1.x, _1.y);
            default:
                return this
        }
        return this
    }
    , isc.A.prepareForDragging = function isc_DrawPane_prepareForDragging() {
        if (this.canDrag) {
            var _1 = this.getDrawItem(isc.EH.lastEvent.x, isc.EH.lastEvent.y);
            if (_1 && _1.canDrag) {
                isc.EH.dragTarget = _1;
                isc.EH.dragOperation = "drag"
            }
        }
        return true
    }
    , isc.A.clear = function isc_DrawPane_clear() {
        this.Super("clear", arguments);
        this.erase()
    }
    , isc.A.drawBounds = function isc_DrawPane_drawBounds(_1) {
        isc.DrawRect.create({autoDraw: true, drawPane: this, left: Math.round(_1.bounds.x), top: Math.round(_1.bounds.y - _1.bounds.height), width: Math.round(_1.bounds.width), height: Math.round(_1.bounds.height)}, {lineColor: "#FF0000", lineOpacity: 0.1, lineWidth: 1, linePattern: "solid"});
        if (_1.nodes && _1.nodes.length) {
            for (var i = 0; i < _1.nodes.length; ++i) {
                this.drawBounds(_1.nodes[i])
            }
        }
    }
    , isc.A.erase = function isc_DrawPane_erase(_1) {
        if (_1) {
            for (var i = 0; i < this.drawItems.length; i++) {
                this.drawItems[i].destroy(true)
            }
        } else if (this.isDrawn()) {
            for (var i = 0; i < this.drawItems.length; i++) {
                this.drawItems[i].erase(true)
            }
        }
        this.drawItems = [];
        this.canvasItems = [];
        this.createQuadTree();
        if (this.drawingType == "bitmap")
            this.redrawBitmap()
    }
    , isc.A.destroyItems = function isc_DrawPane_destroyItems() {
        this.erase(true)
    }
    , isc.A.destroy = function isc_DrawPane_destroy() {
        this.Super("destroy", arguments);
        for (var i = 0; i < this.drawItems.length; i++) {
            this.drawItems[i].destroy()
        }
    }
    , isc.A.addDrawItem = function isc_DrawPane_addDrawItem(_1, _2) {
        if (_1.drawPane == this)
            return;
        if (_1.$if)
            _1.erase();
        _1.drawPane = this;
        if (!this.drawItems.contains(_1))
            this.drawItems.add(_1);
        if (_2 == null)
            _2 = true;
        if (_2 && this.isDrawn()) {
            _1.draw()
        }
    }
    , isc.A.getInnerHTML = function isc_DrawPane_getInnerHTML() {
        var _1 = this.drawingType;
        if (_1 == "vml") {
            return isc.Browser.isTransitional ? this.startTagVML('GROUP') + " ID='" + this.getID() + "$435' STYLE='position:absolute;left:5px;top:5px;width:" + (this.getInnerContentWidth()) + "px; height:" + (this.getInnerContentHeight()) + "px;rotation:" + (this.rotation) + ";' coordsize='" + (this.getInnerContentWidth()) + ", " + (this.getInnerContentHeight()) + "' coordorig='0 0'>" + this.endTagVML('GROUP') : this.startTagVML('GROUP') + " ID='" + this.getID() + "$435' STYLE='left:0px;top:0px;width:" + this.getInnerContentWidth() + "px; height:" + this.getInnerContentHeight() + "px;' coordsize='" + this.getInnerContentWidth() + ", " + this.getInnerContentHeight() + "' coordorig='0 0'>" + this.endTagVML('GROUP')
        } else if (_1 == "bitmap") {
            return"<CANVAS ID='" + this.getID() + "$436'" + " WIDTH='" + this.getWidth() + "' HEIGHT='" + this.getHeight() + "'></CANVAS>"
        } else if (_1 == "svg") {
            return"<IFRAME HEIGHT='100%' WIDTH='100%' SCROLLING='NO' FRAMEBORDER='0' SRC='" + isc.Page.getHelperDir() + "DrawPane.svg?isc_dp_id=" + this.getID() + "'></IFRAME>"
        } else {
            this.logWarn("DrawPane getInnerHTML: '" + _1 + "' is not a supported drawingType");
            return this.Super("getInnerHTML", arguments)
        }
    }
    , isc.A.getPrintHTML = function isc_DrawPane_getPrintHTML() {
        if (this.drawingType == "bitmap") {
            var _1 = document.getElementById(this.getID() + "$436");
            return"<img src='" + _1.toDataURL() + "' width='" + this.getWidth() + "' height='" + this.getHeight() + "' />"
        } else if (this.drawingType == "vml") {
            var _2 = document.getElementById(this.getID() + "$435");
            return _2.parentElement.innerHTML
        } else if (this.drawingType == "svg") {
            var s = new XMLSerializer();
            var _4 = s.serializeToString(this.$44n);
            var _5 = s.serializeToString(this.$44o);
            return"<svg>" + _4 + _5 + "</svg>"
        } else {
            return""
        }
    }
    , isc.A.drawChildren = function isc_DrawPane_drawChildren() {
        this.Super("drawChildren", arguments);
        for (var i = 0; i < this.drawItems.length; i++) {
            var _2 = this.drawItems[i];
            _2.draw()
        }
    }
    , isc.A.getBitmapContext = function isc_DrawPane_getBitmapContext() {
        if (this.$437)
            return this.$437;
        this.$438 = isc.Element.get(this.getID() + "$436");
        if (!this.$438) {
            this.logWarn("DrawPane failed to get CANVAS element handle");
            return
        }
        this.$437 = this.$438.getContext("2d");
        if (!this.$437) {
            this.logWarn("DrawPane failed to get CANVAS 2d bitmap context");
            return
        }
        return this.$437
    }
    , isc.A.redraw = function isc_DrawPane_redraw(_1) {
        if (this.drawingType == "bitmap") {
            this.Super("redraw", arguments);
            this.$437 = null;
            this.redrawBitmapNow()
        }
    }
    , isc.A.redrawBitmap = function isc_DrawPane_redrawBitmap() {
        if (this.$439)
            return;
        isc.Timer.setTimeout({target: this, methodName: "redrawBitmapNow"}, 0);
        this.$439 = true
    }
    , isc.A.redrawBitmapNow = function isc_DrawPane_redrawBitmapNow() {
        if (!this.isDrawn())
            return;
        this.$439 = false;
        var _1 = this.getBitmapContext();
        if (!_1)
            return;
        var _2 = document.getElementById(this.getID() + "$436");
        _2.width = _2.width;
        _1.clearRect(0, 0, this.getWidth(), this.getHeight());
        for (var i = 0; i < this.drawItems.length; i++) {
            if (!this.drawItems[i].hidden) {
                this.drawItems[i].drawBitmap(_1)
            }
        }
    }
    , isc.A.drawing2screen = function isc_DrawPane_drawing2screen(_1) {
        return[Math.round((_1[0] - this.$431) * this.zoomLevel), Math.round((_1[1] - this.$432) * this.zoomLevel), Math.round(_1[2] * this.zoomLevel), Math.round(_1[3] * this.zoomLevel)]
    }
    , isc.A.screen2drawing = function(screenRect) {
        return[screenRect[0] / this.zoomLevel + this.$431, screenRect[1] / this.zoomLevel + this.$432, screenRect[2] / this.zoomLevel, screenRect[3] / this.zoomLevel]
    }
    , isc.A.$44a = function isc_DrawPane__setViewBox(_1, _2, _3, _4) {
        var _5 = this.drawingType;
        this.$431 = _1;
        this.$432 = _2;
        this.$433 = _3;
        this.$434 = _4;
        this.$44b();
        if (_5 == "vml") {
            if (!this.$44c) {
                this.$44c = isc.Element.get(this.getID() + "$435")
            }
            if (this.$44c) {
                this.$44c.coordorigin.x = _1;
                this.$44c.coordorigin.y = _2;
                this.$44c.coordsize.x = _3;
                this.$44c.coordsize.y = _4
            }
        } else if (_5 == "svg") {
            if (!this.$44n) {
                var _6 = this;
                setTimeout(function() {
                    _6.$44a(_1, _2, _3, _4)
                }, 100)
            } else {
                this.$44n.setAttributeNS(null, "transform", "translate(" + -_1 + "," + -_2 + ")")
            }
        }
    }
    , isc.A.addCanvasItem = function isc_DrawPane_addCanvasItem(_1) {
        this.canvasItems.add(_1);
        _1.drawPane = this;
        _1.$44e = _1.left;
        _1.$44f = _1.top;
        _1.$44g = _1.width;
        _1.$44h = _1.height;
        this.addChild(_1);
        this.$44i(_1);
        return _1
    }
    , isc.A.$85q = function isc_DrawPane__createSimpleGradientSVG(_1, _2) {
        var _3, i, _5, _6, _7, _8, _9;
        if (!isc.Browser.isWebKit) {
            _3 = "<linearGradient id='" + _1
            "' x1='" + _2.x1 + "' x2='" + _2.x2 + "' y1='" + _2.y1 + "' y2='" + _2.y2 + "'>";
            _5 = _2.startColor;
            _6 = 0.0;
            _7 = "1";
            _3 += "<stop stop-color='" + _5 + "' offset='" + _6 + "' stop-opacity='" + _7 + "'/>";
            _5 = _2.endColor;
            _6 = 1.0;
            _7 = "1";
            _3 += "<stop stop-color='" + _5 + "' offset='" + _6 + "' stop-opacity='" + _7 + "'/>";
            _3 += "</linearGradient>";
            _8 = this.$44m.createRange();
            _8.setStart(this.$44o, 0);
            this.$44o.appendChild(_8.createContextualFragment(_3))
        } else {
            _3 = this.$44m.createElementNS("http://www.w3.org/2000/svg", "linearGradient");
            _3.setAttributeNS(null, "id", _1);
            _3.setAttributeNS(null, "x1", _2.x1);
            _3.setAttributeNS(null, "x2", _2.x2);
            _3.setAttributeNS(null, "y1", _2.y1);
            _3.setAttributeNS(null, "y2", _2.y2);
            _9 = this.$44m.createElementNS("http://www.w3.org/2000/svg", "stop");
            _9.setAttributeNS(null, "stop-color", _2.startColor);
            _9.setAttributeNS(null, "offset", "0.0");
            _9.setAttributeNS(null, "stop-opacity", "1");
            _3.appendChild(_9);
            _9 = this.$44m.createElementNS("http://www.w3.org/2000/svg", "stop");
            _9.setAttributeNS(null, "stop-color", _2.endColor);
            _9.setAttributeNS(null, "offset", "1.0");
            _9.setAttributeNS(null, "stop-opacity", "1");
            _3.appendChild(_9);
            this.$44o.appendChild(_3)
        }
    }
    , isc.A.$83q = function isc_DrawPane__createLinearGradientSVG(_1, _2) {
        var _3, i, _5, _6, _7, _8, _9;
        if (!isc.Browser.isWebKit) {
            _3 = "<linearGradient id='" + _1 + "' x1='" + _2.x1 + "' x2='" + _2.x2 + "' y1='" + _2.y1 + "' y2='" + _2.y2 + "'>";
            for (var i = 0; i < _2.colorStops.length; ++i) {
                _5 = _2.colorStops[i].color;
                _6 = _2.colorStops[i].offset;
                _7 = _2.colorStops[i].opacity || "1";
                ;
                _3 += "<stop stop-color='" + _5 + "' offset='" + _6 + "' stop-opacity='" + _7 + "'/>"
            }
            _3 += "</linearGradient>";
            _8 = this.$44m.createRange();
            _8.setStart(this.$44o, 0);
            this.$44o.appendChild(_8.createContextualFragment(_3))
        } else {
            _3 = this.$44m.createElementNS("http://www.w3.org/2000/svg", "linearGradient");
            _3.setAttributeNS(null, "id", _1);
            _3.setAttributeNS(null, "x1", _2.x1);
            _3.setAttributeNS(null, "x2", _2.x2);
            _3.setAttributeNS(null, "y1", _2.y1);
            _3.setAttributeNS(null, "y2", _2.y2);
            for (var i = 0; i < _2.colorStops.length; ++i) {
                _9 = this.$44m.createElementNS("http://www.w3.org/2000/svg", "stop");
                _9.setAttributeNS(null, "stop-color", _2.colorStops[i].color);
                _9.setAttributeNS(null, "offset", _2.colorStops[i].offset);
                _9.setAttributeNS(null, "stop-opacity", _2.colorStops[i].opacity || "1");
                _3.appendChild(_9)
            }
            this.$44o.appendChild(_3)
        }
    }
    , isc.A.$83r = function isc_DrawPane__createRadialGradientSVG(_1, _2) {
        var _3, i, _5, _6, _7, _8, _9;
        if (!isc.Browser.isWebKit) {
            _3 = "<radialGradient id='" + _1 + "' cx='" + _2.cx + "' cy='" + _2.cy + "' r='" + _2.r + "' fx='" + _2.fx + "' fy='" + _2.fy + "'>";
            for (var i = 0; i < _2.colorStops.length; ++i) {
                _5 = _2.colorStops[i].color;
                _6 = _2.colorStops[i].offset;
                _7 = _2.colorStops[i].opacity || "1";
                ;
                _3 += "<stop stop-color='" + _5 + "' offset='" + _6 + "' stop-opacity='" + _7 + "'/>"
            }
            _3 += "</radialGradient>";
            _8 = this.$44m.createRange();
            _8.setStart(this.$44o, 0);
            this.$44o.appendChild(_8.createContextualFragment(_3))
        } else {
            _3 = this.$44m.createElementNS("http://www.w3.org/2000/svg", "radialGradient");
            _3.setAttributeNS(null, "id", _1);
            _3.setAttributeNS(null, "cx", _2.cx);
            _3.setAttributeNS(null, "cy", _2.cy);
            _3.setAttributeNS(null, "r", _2.r);
            _3.setAttributeNS(null, "fx", _2.fx);
            _3.setAttributeNS(null, "fy", _2.fy);
            for (i = 0; i < _2.colorStops.length; ++i) {
                _9 = this.$44m.createElementNS("http://www.w3.org/2000/svg", "stop");
                _9.setAttributeNS(null, "stop-color", _2.colorStops[i].color);
                _9.setAttributeNS(null, "offset", _2.colorStops[i].offset);
                _9.setAttributeNS(null, "stop-opacity", _2.colorStops[i].opacity || "1");
                _3.appendChild(_9)
            }
            this.$44o.appendChild(_3)
        }
    }
    , isc.A.$44i = function isc_DrawPane__updateCanvasItemToViewBox(_1) {
        _1.synchingToPane = true;
        _1.setRect(this.drawing2screen([_1.$44e, _1.$44f, _1.$44g, _1.$44h]));
        if (_1.drawBorderSize) {
            _1.getStyleHandle().borderWidth = Math.max(1, _1.drawBorderSize * this.zoomLevel) + "px"
        }
        delete _1.synchingToPane
    }
    , isc.A.$44b = function isc_DrawPane__updateItemsToViewBox() {
        for (var i = 0, _2; i < this.canvasItems.length; i++) {
            this.$44i(this.canvasItems[i])
        }
        if (this.drawingType == "vml") {
            for (var i = 0, _3, _4; i < this.drawItems.length; i++) {
                _3 = this.drawItems[i];
                if (isc.isA.DrawLabel(_3)) {
                    _3.$44j.fontSize = _3.$44k * this.zoomLevel;
                    if (!_3.synchTextMove) {
                        _4 = this.drawing2screen([_3.left, _3.top, 0, 0]);
                        _3.$44j.left = _4[0];
                        _3.$44j.top = _4[1]
                    }
                } else {
                    _3.$44l(_3.lineWidth * this.zoomLevel)
                }
            }
        }
    }
    , isc.A.pan = function isc_DrawPane_pan(_1, _2) {
        this.$44a(this.$431 + _1 / this.zoomLevel, this.$432 + _2 / this.zoomLevel, this.$433, this.$434);
        if (this.drawingType === 'bitmap') {
            this.translate = this.translate || [0, 0];
            this.translate[0] -= _1;
            this.translate[1] -= _2;
            this.redrawBitmap()
        }
    }
    , isc.A.zoom = function isc_DrawPane_zoom(_1) {
        var _2 = _1 / this.zoomLevel, _3 = this.$433 / _2, _4 = this.$434 / _2;
        this.zoomLevel = _1;
        this.$44a(this.$431 + this.$433 / 2 - _3 / 2, this.$432 + this.$434 / 2 - _4 / 2, _3, _4);
        if (this.drawingType === 'bitmap') {
            var _5 = document.getElementById(this.getID() + "$436");
            if (_5) {
                _5.style['-webkit-transform'] = 'scale(' + this.zoomLevel + ',' + this.zoomLevel + ')'
            }
        }
    }
    , isc.A.rotate = function isc_DrawPane_rotate(_1) {
        var _2 = this.drawingType;
        this.rotation = _1;
        if (_2 === "vml") {
            if (!this.$44c) {
                this.$44c = isc.Element.get(this.getID() + "$435")
            }
            if (this.$44c) {
                this.$44c.style.rotation = _1
            }
        } else if (_2 === "svg") {
            if (!this.$44n) {
                var _3 = this;
                setTimeout(function() {
                    _3.rotate(_1)
                }, 100)
            } else {
                this.$44n.setAttributeNS(null, "transform", "rotate(" + _1 + ")")
            }
        } else if (this.drawingType === 'bitmap') {
            var _4 = document.getElementById(this.getID() + "$436");
            if (_4) {
                _4.style['-webkit-transform'] = 'rotate(' + this.rotation + 'deg)'
            }
        }
    }
    , isc.A.createSimpleGradient = function isc_DrawPane_createSimpleGradient(_1, _2) {
        this.gradients[_1] = _2;
        if (this.drawingType === "svg") {
            this.$85q(_1, _2)
        }
        return _1
    }
    , isc.A.createLinearGradient = function isc_DrawPane_createLinearGradient(_1, _2) {
        this.gradients[_1] = _2;
        var _3 = this.drawingType;
        if (_3 === "svg") {
            this.$83q(_1, _2)
        }
        return _1
    }
    , isc.A.createRadialGradient = function isc_DrawPane_createRadialGradient(_1, _2) {
        this.gradients[_1] = _2;
        var _3 = this.drawingType;
        if (_3 == "svg") {
            this.$83r(_1, _2)
        }
        return _1
    }
    , isc.A.shouldDeferDrawing = function isc_DrawPane_shouldDeferDrawing(_1) {
        if (this.drawingType == "svg" && !this.$44m) {
            if (!this.$63q) {
                this.$63q = [_1]
            } else {
                this.$63q.add(_1)
            }
            return true
        } else if (this.drawingType == "vml" && !this.getHandle()) {
            if (!this.$63q) {
                this.$63q = [_1]
            } else {
                this.$63q.add(_1)
            }
            if (!this.deferring) {
                var _2 = this;
                _2.deferring = true;
                setTimeout(function() {
                    if (_2.$63q) {
                        _2.$63q.map("draw");
                        delete _2.$63q
                    }
                    delete _2.deferring
                }, 10)
            }
            return true
        }
        return false
    }
    , isc.A.cancelDeferredDraw = function isc_DrawPane_cancelDeferredDraw(_1) {
        if (this.$63q && this.$63q.contains(_1)) {
            this.$63q.remove(_1);
            return true
        }
        return false
    }
    , isc.A.svgLoaded = function isc_DrawPane_svgLoaded() {
        this.$44m = this.getHandle().firstChild.contentDocument;
        this.$44d = this.$44m.getElementById("isc_svg_body");
        this.$44n = this.$44m.getElementById("isc_svg_box");
        this.$44o = this.$44m.getElementById("isc_svg_defs");
        if (this.$63q) {
            this.$63q.map("draw");
            delete this.$63q
        }
    }
    );
    isc.B._maxIndex = isc.C + 38;
    isc.A = isc.DrawPane;
    isc.B = isc._allFuncs;
    isc.C = isc.B._maxIndex;
    isc.D = isc._funcClasses;
    isc.D[isc.C] = isc.A.Class;
    isc.A.defaultDrawingType = isc.Browser.defaultDrawingType;
    isc.A.$44p = [];
    isc.B.push(isc.A.getDefaultDrawPane = function isc_c_DrawPane_getDefaultDrawPane(_1) {
        if (!_1)
            _1 = this.defaultDrawingType;
        if (this.$44p[_1])
            return this.$44p[_1];
        var _2 = this.$44p[_1] = this.create({drawingType: _1, width: isc.Page.getScrollWidth(), height: isc.Page.getScrollHeight(), autoDraw: true});
        _2.sendToBack();
        return _2
    }
    , isc.A.addrgb = function isc_c_DrawPane_addrgb(_1, _2) {
        var _3 = Array(parseInt('0x' + _1.substring(1, 3), 16), parseInt('0x' + _1.substring(3, 5), 16), parseInt('0x' + _1.substring(5, 7), 16));
        var _4 = Array(parseInt('0x' + _2.substring(1, 3), 16), parseInt('0x' + _2.substring(3, 5), 16), parseInt('0x' + _2.substring(5, 7), 16));
        var _5 = _3[0] + _4[0];
        _5 = Math.round(_5 > 0xff ? 0xff : _5).toString(16);
        _5 = _5.length === 1 ? "0" + _5 : _5;
        var _6 = _3[1] + _4[1];
        _6 = Math.round(_6 > 0xff ? 0xff : _6).toString(16);
        _6 = _6.length === 1 ? "0" + _6 : _6;
        var _7 = _3[2] + _4[2];
        _7 = Math.round(_7 > 0xff ? 0xff : _7).toString(16);
        _7 = _7.length === 1 ? "0" + _7 : _7;
        return'#' + _5 + _6 + _7
    }
    , isc.A.subtractrgb = function isc_c_DrawPane_subtractrgb(_1, _2) {
        var _3 = Array(parseInt('0x' + _1.substring(1, 3), 16), parseInt('0x' + _1.substring(3, 5), 16), parseInt('0x' + _1.substring(5, 7), 16));
        var _4 = Array(parseInt('0x' + _2.substring(1, 3), 16), parseInt('0x' + _2.substring(3, 5), 16), parseInt('0x' + _2.substring(5, 7), 16));
        var _5 = _3[0] - _4[0];
        _5 = Math.round(_5 < 0x0 ? 0x0 : _5).toString(16);
        _5 = _5.length === 1 ? "0" + _5 : _5;
        var _6 = _3[1] - _4[1];
        _6 = Math.round(_6 < 0x0 ? 0x0 : _6).toString(16);
        _6 = _6.length === 1 ? "0" + _6 : _6;
        var _7 = _3[2] - _4[2];
        _7 = Math.round(_7 < 0x0 ? 0x0 : _7).toString(16);
        _7 = _7.length === 1 ? "0" + _7 : _7;
        return'#' + _5 + _6 + _7
    }
    , isc.A.mixrgb = function isc_c_DrawPane_mixrgb(_1, _2) {
        return _2.charAt(0) === '+' ? isc.DrawPane.addrgb(_1, _2.substring(1)) : _2.charAt(0) === '-' ? isc.DrawPane.subtractrgb(_1, _2.substring(1)) : _2
    }
    , isc.A.hex2rgb = function isc_c_DrawPane_hex2rgb(_1, _2) {
        var _3 = _1.split('#')[1];
        var _4 = _3.length / 3;
        var _5 = _3.substring(0, _4);
        var _6 = _3.substring(_4, _4 * 2);
        var _7 = _3.substring(_4 * 2, _4 * 3);
        var _8 = parseInt(_5.toUpperCase(), 16);
        var _9 = parseInt(_6.toUpperCase(), 16);
        var _10 = parseInt(_7.toUpperCase(), 16);
        var _11 = (typeof(_2) !== 'undefined') ? 'rgba(' + _8 + ',' + _9 + ',' + _10 + ',' + _2 + ')' : 'rgb(' + _8 + ',' + _9 + ',' + _10 + ')';
        return _11
    }
    , isc.A.rgb2hex = function isc_c_DrawPane_rgb2hex(_1) {
        var _2 = "", v, i;
        var _5 = /([0-9]+)[, ]+([0-9]+)[, ]+([0-9]+)/;
        var h = _5.exec(_1);
        for (i = 1; i < 4; i++) {
            v = parseInt(h[i], 10).toString(16);
            if (v.length == 1) {
                _2 += "0" + v
            } else {
                _2 += v
            }
        }
        return("#" + _2)
    }
    );
    isc.B._maxIndex = isc.C + 6;
    isc.defineClass("DrawItem").addProperties({lineWidth: 3, lineColor: "#808080", lineOpacity: 1.0, linePattern: "solid", lineCap: "round", fillGradient: null, fillOpacity: 1.0, shadow: null, rotation: 0, scale: null, autoOffset: true, canDrag: false, getBoundingBox: function() {
            return[]
        }, isInBounds: function(_1, _2) {
            var b = this.getBoundingBox();
            var _4 = (_1 >= b[0]) && (_1 <= b[2]) && (_2 >= b[1]) && (_2 <= b[3]);
            return _4
        }, isPointInPath: function(_1, _2) {
            if (!this.isInBounds(_1, _2)) {
                return false
            }
            if (this.drawPane.drawingType == 'bitmap') {
                var _3 = this.drawPane.getBitmapContext();
                _3.save();
                var _4 = this.lineColor, _5 = this.lineOpacity;
                this.lineColor = "#000000";
                this.lineOpacity = 0.0;
                this.drawStroke(_3);
                _3.restore();
                this.lineColor = _4;
                this.lineOpacity = _5;
                var _6 = _3.isPointInPath(parseFloat(_1), parseFloat(_2));
                return _6
            }
            return true
        }, getHoverTarget: function(_1, _2) {
            return this.drawPane
        }, $kr: function(_1) {
            return false
        }, $k2: function() {
        }, focus: function(_1) {
        }, visibleAtPoint: function(_1, _2) {
            return true
        }, getDragAppearance: function(_1) {
            return"target"
        }, bringToFront: function() {
        }, moveToEvent: function(_1, _2) {
            this.moveTo(_1, _2)
        }, dragStartDistance: 1, getRect: function() {
            var _1 = this.getBoundingBox();
            return[_1[0], _1[1], _1[2] - _1[0], _1[3] - _1[1]]
        }, getPageLeft: function() {
            return this.getBoundingBox()[0]
        }, getPageTop: function() {
            return this.getBoundingBox()[1]
        }, dragStart: function(_1, _2) {
            var _3 = this.getBoundingBox();
            var _4 = _3[0];
            var _5 = _3[1];
            var _6 = this.drawPane.normalize(_1.x, _1.y);
            this.dragOffsetX = _6.x - this.drawPane.getPageLeft() - _4;
            this.dragOffsetY = _6.y - this.drawPane.getPageTop() - _5;
            return true
        }, dragMove: function(_1, _2) {
            var _3 = this.drawPane.normalize(_1.x, _1.y);
            var x = _3.x - this.drawPane.getPageLeft() - this.dragOffsetX;
            var y = _3.y - this.drawPane.getPageTop() - this.dragOffsetY;
            this.moveTo(x, y);
            return true
        }, dragStop: function(_1, _2) {
            var _3 = this.drawPane.normalize(_1.x, _1.y);
            var x = _3.x - this.drawPane.getPageLeft() - this.dragOffsetX;
            var y = _3.y - this.drawPane.getPageTop() - this.dragOffsetY;
            this.moveTo(x, y);
            return true
        }, click: function() {
            return true
        }, mouseDown: function() {
            return true
        }, mouseUp: function() {
            return true
        }, mouseMove: function() {
            return true
        }, mouseOut: function() {
            return true
        }, mouseOver: function() {
            return true
        }, init: function() {
            this.Super("init");
            if (this.ID == null || window[this.ID] != this) {
                isc.ClassFactory.addGlobalID(this)
            }
            this.drawItemID = isc.DrawItem.$44q++;
            if (this.drawGroup) {
                this.eventParent = this.drawGroup;
                this.drawGroup.drawItems.add(this)
            } else if (this.drawPane) {
                this.eventParent = this.drawPane;
                this.drawPane.drawItems.add(this);
                var _1 = this.getBoundingBox();
                if (_1 && _1.length === 4) {
                    this.item = {x: _1[0], y: _1[1], width: _1[2] - _1[0], height: _1[3] - _1[1], shape: this};
                    if (!(isNaN(this.item.width) || isNaN(this.item.height))) {
                        this.drawPane.quadTree.insert(this.item)
                    }
                }
            }
            if (this.autoDraw)
                this.draw()
        }, draw: function() {
            if (this.$if) {
                this.logWarn("DrawItem already drawn - exiting draw()");
                return
            }
            if (this.drawGroup) {
                if (!this.drawGroup.$if) {
                    this.logWarn("Attempted draw into an undrawn group - calling draw() on the group now");
                    this.drawGroup.draw();
                    if (!this.drawGroup.$if)
                        return
                }
                this.drawPane = this.drawGroup.drawPane
            } else {
                if (!this.drawPane)
                    this.drawPane = isc.DrawPane.getDefaultDrawPane(this.drawingType)
            }
            var _1 = this.drawPane, _2 = _1.getDrawnState();
            if (!_1.isDrawn() && _1.getDrawnState() != isc.Canvas.HANDLE_DRAWN)
                return;
            if (_1.shouldDeferDrawing(this))
                return;
            var _3 = this.drawingType = _1.drawingType;
            if (_3 == "vml") {
                this.drawingVML = true
            } else if (_3 == "svg") {
                this.drawingSVG = true
            } else if (_3 == "bitmap") {
                this.drawingBitmap = true
            }
            if (this.drawingVML) {
                if (isc.isA.DrawLabel(this) && !this.synchTextMove) {
                    this.$44t = _1.getHandle()
                } else if (this.drawGroup) {
                    this.$44t = this.drawGroup.$44u
                } else {
                    this.$44t = isc.Element.get(_1.getID() + "$435")
                }
                var _4 = "isc_DrawItem_" + this.drawItemID;
                isc.Element.insertAdjacentHTML(this.$44t, this.drawToBack ? "afterBegin" : "beforeEnd", this.$44v(_4));
                this.$44u = isc.Element.get(_4);
                if (!isc.isA.DrawGroup(this) && !isc.isA.DrawImage(this) && !isc.isA.DrawLabel(this)) {
                    this.$44w = this.$44u.firstChild;
                    this.$44x = this.$44w.nextSibling
                }
                if (isc.isA.DrawLabel(this)) {
                    if (this.synchTextMove) {
                        this.$44j = this.$44u.firstChild.style
                    } else {
                        this.$44j = this.$44u.style
                    }
                }
            } else if (this.drawingSVG) {
                this.$44m = _1.getHandle().firstChild.contentDocument;
                this.$44y = this.drawGroup ? this.drawGroup.$44z : this.$44m.getElementById("isc_svg_box");
                if (this.$44y == null) {
                    this.logWarn("DrawItem.draw() - Attempt to render into drawPane using svg unable to" + " access svgContainer - svg frame may not have loaded.");
                    return
                }
                this.$44o = this.$44m.getElementById("isc_svg_defs");
                var _4 = "isc_DrawItem_" + this.drawItemID;
                var _5;
                if (!isc.Browser.isWebKit) {
                    var _6 = this.$44m.createRange();
                    _6.setStart(this.$44y, 0);
                    _5 = _6.createContextualFragment(this.$440(_4))
                } else {
                    _5 = this.$440(_4)
                }
                if (this.drawToBack && this.$44y.firstChild) {
                    this.$44y.insertBefore(_5, this.$44y.firstChild);
                    this.$44z = this.$44y.firstChild
                } else {
                    this.$44y.appendChild(_5);
                    this.$44z = this.$44y.lastChild
                }
            } else if (this.drawingBitmap) {
                _1.redrawBitmap()
            } else {
                this.logWarn("DrawItem: '" + _3 + "' is not a supported drawingType");
                return
            }
            this.$if = true;
            if (this.knobs) {
                for (var i = 0; i < this.knobs.length; i++)
                    this.$634(this.knobs[i])
            }
        }, isDrawn: function() {
            return!!this.$if
        }, moved: function() {
            this.updateControlKnobs()
        }, resized: function() {
            this.updateControlKnobs()
        }, rotated: function() {
            this.updateControlKnobs()
        }, scaled: function() {
            this.updateControlKnobs()
        }, $634: function(_1) {
            var _2 = isc.DrawItem.$632(_1)
            if (!this[_2]) {
                this.logWarn("DrawItem specfied with knobType:" + _1 + " but no " + _2 + " function exists to show the knobs. Ignoring")
            } else {
                this[_2]()
            }
        }, $635: function(_1) {
            var _2 = isc.DrawItem.$632(_1, true)
            if (!this[_2]) {
                this.logWarn("DrawItem specfied with knobType:" + _1 + " but no " + _2 + " function exists to hide the knobs.")
            } else {
                this[_2]()
            }
        }, showKnobs: function(_1) {
            if (isc.isAn.Array(_1)) {
                for (var i = 0; i < _1.length; i++) {
                    this.showKnobs(_1[i])
                }
                return
            }
            if (!this.knobs)
                this.knobs = [];
            if (this.knobs.contains(_1))
                return;
            this.knobs.add(_1);
            if (this.$if) {
                this.$634(_1)
            }
        }, hideKnobs: function(_1) {
            if (!this.knobs)
                return;
            if (isc.isAn.Array(_1)) {
                for (var i = 0; i < _1.length; i++) {
                    this.hideKnobs(_1[i])
                }
                return
            }
            if (this.knobs.contains(_1))
                this.knobs.remove(_1);
            if (this.$if) {
                this.$635(_1)
            }
        }, moveKnobPoint: "TL", $636: function(_1) {
            var x, y;
            x = _1.contains("L") ? this.left : (_1.contains("R") ? (this.left + this.width) : (this.left + Math.round(this.width / 2)));
            y = _1.contains("T") ? this.top : (_1.contains("B") ? (this.top + this.height) : (this.top + Math.round(this.height / 2)));
            return[x, y]
        }, showMoveKnobs: function() {
            var _1 = this.$636(this.moveKnobPoint), x = _1[0], y = _1[1];
            if (this.moveKnobOffset) {
                x += this.moveKnobOffset[0];
                y += this.moveKnobOffset[1]
            }
            this.$637 = this.createAutoChild("moveKnob", {_constructor: "DrawKnob", x: x, y: y, drawPane: this.drawPane, autoDraw: true});
            this.observe(this.$637, "updatePoints", "observer.moveBy(dX,dY)")
        }, hideMoveKnobs: function() {
            if (this.$637) {
                this.$637.destroy();
                delete this.$637
            }
        }, resizeKnobPoints: ["TL", "TR", "BL", "BR"], getResizeKnobProperties: function(_1) {
        }, showResizeKnobs: function() {
            var _1 = this.resizeKnobPoints;
            for (var i = 0; i < _1.length; i++) {
                var _3 = _1[i], _4 = this.$636(_3), x = _4[0], y = _4[1];
                var _7 = isc.addProperties({}, this.getResizeKnobProperties(_3), {_constructor: "DrawKnob", targetShape: this, point: _3, x: x, y: y, drawPane: this.drawPane, autoDraw: true});
                var _8 = this.createAutoChild("resizeKnob", _7);
                _8.addProperties({updatePoints: function(_9, _10, _11, _12) {
                        return this.targetShape.handleResizeKnobMove(this.point, _9, _10, _11, _12)
                    }});
                if (!this.$638)
                    this.$638 = [];
                this.$638.add(_8)
            }
        }, handleResizeKnobMove: function(_1, _2, _3, _4, _5) {
            var _6 = this.left, _7 = this.top, _8 = this.width, _9 = this.height, _10;
            if (_1.contains("L")) {
                _6 = _2;
                _8 -= _4;
                _10 = true
            } else if (_1.contains("R")) {
                _8 += _4
            }
            if (_1.contains("T")) {
                _7 = _3;
                _9 -= _5;
                _10 = true
            } else if (_1.contains("B")) {
                _9 += _5
            }
            if (_8 < 1 || _9 < 1)
                return false;
            this.resizeTo(_8, _9);
            if (_10)
                this.moveTo(_6, _7)
        }, hideResizeKnobs: function() {
            if (this.$638) {
                this.$638.map("destroy");
                delete this.$638
            }
        }, updateControlKnobs: function() {
            if (this.$637) {
                var _1 = this.$636(this.moveKnobPoint);
                if (this.moveKnobOffset) {
                    _1[0] += this.moveKnobOffset[0];
                    _1[1] += this.moveKnobOffset[1]
                }
                var _2 = this.drawPane.drawing2screen(_1);
                this.$637.setCenterPoint(_2[0], _2[1])
            }
            if (this.$638) {
                for (var i = 0; i < this.$638.length; i++) {
                    var _4 = this.$638[i], _1 = this.$636(_4.point);
                    var _2 = this.drawPane.drawing2screen(_1);
                    _4.setCenterPoint(_2[0], _2[1])
                }
            }
        }, setDrawPane: function(_1) {
            if (_1 == this.drawPane)
                return;
            if (_1 == null) {
                this.erase();
                this.drawPane = null;
                return
            }
            _1.addDrawItem(this)
        }, $63r: function() {
            return false
        }, $63s: function() {
            return false
        }, erase: function(_1) {
            if (!this.$if) {
                if (this.drawPane && this.drawPane.cancelDeferredDraw(this))
                    return;
                this.logWarn("DrawItem not yet drawn - exiting erase()");
                return
            }
            if (!_1) {
                if (this.drawGroup)
                    this.drawGroup.drawItems.remove(this);
                else {
                    this.drawPane.drawItems.remove(this);
                    this.item && this.drawPane.quadTree.remove(this.item)
                }
            }
            if (this.drawingVML) {
                if (isc.Page.isLoaded())
                    this.$44u.outerHTML = "";
                else
                    this.$44t.removeChild(this.$44u);
                this.$44t = null;
                this.$44u = null;
                this.$44w = null;
                this.$44x = null
            } else if (this.drawingSVG) {
                this.$44y.removeChild(this.$44z);
                this.$44m = null;
                this.$44y = null;
                this.$44z = null
            } else if (this.drawingBitmap && !_1) {
                this.drawPane.redrawBitmap()
            }
            if (this.knobs) {
                for (var i = 0; i < this.knobs.length; i++) {
                    this.$635(this.knobs[i])
                }
            }
            this.$if = false
        }, destroy: function(_1) {
            this.Super("destroy", arguments);
            if (this.destroyed)
                return;
            this.destroying = true;
            this.erase(_1);
            isc.ClassFactory.dereferenceGlobalID(this);
            this.destroyed = true
        }, $44v: function(_1) {
            var _2 = " ON='false", _3 = " ON='false", _4, _5, _6, _7, _8 = "", _9;
            if (this.shadow) {
                _3 = " ON='true' COLOR='" + this.shadow.color + "' OFFSET='" + this.shadow.offset[0] + "pt," + this.shadow.offset[1] + "pt"
            }
            if (this.fillGradient) {
                var _10 = typeof(this.fillGradient) === 'string' ? this.drawPane.gradients[this.fillGradient] : this.fillGradient;
                if (typeof(_10.direction) === 'number' || _10.x1 || typeof(_10.x1) === 'number') {
                    _4 = this.$830(_10, this.getBoundingBox());
                    _5 = _10.direction || 180 * Math.atan2((_4[3] - _4[1]), (_4[2] - _4[0])) / Math.PI;
                    _5 = (270 - _5) % 360;
                    _8 = "' COLORS='";
                    if (_10.startColor && _10.endColor) {
                        _8 += '0% ' + _10.startColor + ', 100% ' + _10.endColor
                    } else if (_10.colorStops && _10.colorStops.length) {
                        for (var i = 0; i < _10.colorStops.length; ++i) {
                            var _12 = _10.colorStops[i];
                            if (typeof(_12.offset) === 'string' && _12.offset.endsWith('%')) {
                                _9 = _12.offset
                            } else {
                                _9 = _12.offset
                            }
                            _8 += _9 + ' ' + _12.color;
                            if (i < _10.colorStops.length - 1) {
                                _8 += ", "
                            }
                        }
                    }
                    _2 = " TYPE='gradient' ANGLE='" + _5 + _8
                } else {
                    _8 = "' COLORS='";
                    for (var i = 0; i < _10.colorStops.length; ++i) {
                        var _12 = _10.colorStops[i];
                        if (typeof(_12.offset) === 'string' && _12.offset.endsWith('%')) {
                            _9 = _12.offset
                        } else {
                            _9 = parseFloat(_12.offset) * 100 + '%'
                        }
                        _8 += _9 + ' ' + _12.color;
                        if (i < _10.colorStops.length - 1) {
                            _8 += ", "
                        }
                    }
                    _2 = " TYPE='gradienttitle' OPACITY='1.0' FOCUS='100%' FOCUSSIZE='0,0' FOCUSPOSITION='0.5,0.5' METHOD='linear" + _8
                }
            } else if (this.fillColor && !isc.isAn.emptyString(this.fillColor)) {
                _2 = " COLOR='" + this.fillColor
            }
            var _13 = isc.SB.concat(this.drawPane.startTagVML(this.vmlElementName), " onmousedown='return ", this.drawPane.getID(), ".mouseDown();' ", " ID='", _1, "' ", this.getAttributesVML(), ">", this.drawPane.startTagVML('STROKE'), ((this.lineColor && this.lineColor != "") ? " COLOR='" + this.lineColor : " ON='false"), "' WEIGHT='", this.lineWidth * this.drawPane.zoomLevel, "px", "' OPACITY='", this.lineOpacity, "' DASHSTYLE='", this.linePattern, "' JOINSTYLE='miter' MITERLIMIT='8.0", "' ENDCAP='" + ((this.lineCap == "butt") ? "flat" : this.lineCap), (this.startArrow ? "' STARTARROW='" + this.startArrow + "' STARTARROWWIDTH='wide" : ""), (this.endArrow ? "' ENDARROW='" + this.endArrow + "' ENDARROWWIDTH='wide" : ""), "'/>", this.drawPane.startTagVML('FILL'), _2, "' OPACITY='", this.fillOpacity, "'/>", this.drawPane.startTagVML('SHADOW'), _3, "'/>", this.drawPane.endTagVML(this.vmlElementName));
            return _13
        }, getAttributesVML: function() {
            return""
        }, $440: function(_1) {
            var _2, _3 = "none", _4 = this.getCenter();
            if (this.fillGradient) {
                var _5 = this.fillGradient;
                if (typeof(_5) === 'object') {
                    if (typeof(_5.direction) === 'number') {
                        this.$830(_5, this.getBoundingBox());
                        _5 = this.drawPane.createSimpleGradient(_1 + "$85r", _5)
                    } else if (typeof(_5.x1) === 'number') {
                        _5 = this.drawPane.createLinearGradient(_1 + "$85r", _5)
                    } else {
                        _5 = this.drawPane.createRadialGradient(_1 + "$85r", _5)
                    }
                }
                _3 = "url(#" + _5 + ")"
            } else if (this.fillColor && this.fillColor.length) {
                _3 = this.fillColor
            }
            if (!isc.Browser.isWebKit) {
                _2 = "<" + this.svgElementName + " id='" + _1 + "' " + this.getAttributesSVG() + " stroke-width='" + this.lineWidth + "px" + "' stroke='" + ((this.lineColor && this.lineColor != "") ? this.lineColor : "none") + "' stroke-opacity='" + this.lineOpacity + "' stroke-dasharray='" + this.$441() + "' stroke-linecap='" + this.lineCap + (this.startArrow && !this.$63r() ? "' marker-start='url(#" + this.$442() + ")" : "") + (this.endArrow && !this.$63s() ? "' marker-end='url(#" + this.$443() + ")" : "") + ((this.rotation && _4 && _4.length === 2) ? "' transform='translate(" + _4[0] + "," + _4[1] + ") rotate(" + this.rotation + ") translate(" + -_4[0] + "," + -_4[1] + ")" : "") + "' fill='" + _3 + "' fill-opacity='" + this.fillOpacity + "'>" + (this.contents || "") + "</" + this.svgElementName + ">"
            } else {
                _2 = this.$44m.createElementNS("http://www.w3.org/2000/svg", this.svgElementName);
                _2.setAttributeNS(null, "id", _1);
                _2.setAttributeNS(null, "stroke-width", this.lineWidth + "px");
                _2.setAttributeNS(null, "stroke-opacity", this.lineOpacity);
                _2.setAttributeNS(null, "stroke-dasharray", this.$441());
                _2.setAttributeNS(null, "stroke-linecap", this.lineCap);
                _2.setAttributeNS(null, "stroke", this.lineColor || "none");
                _2.setAttributeNS(null, "fill", _3);
                _2.setAttributeNS(null, "fill-opacity", this.fillOpacity);
                if (this.rotation) {
                    _2.setAttributeNS(null, "transform", "translate(" + _4[0] + "," + _4[1] + ") rotate(" + this.rotation + ") translate(" + -_4[0] + "," + -_4[1] + ")")
                }
                if (this.startArrow && !this.$63r()) {
                    _2.setAttributeNS(null, "markerStart", "url(#" + this.$442() + ")")
                }
                if (this.endArrow && !this.$63s()) {
                    _2.setAttributeNS(null, "markerEnd", "url(#" + this.$443() + ")")
                }
                var _6, _7;
                if (this.getAttributesSVG().match(/=/g).length === 1) {
                    _7 = this.getAttributesSVG().split('=');
                    _2.setAttributeNS(null, _7[0], _7[1].replace(/'/g, ""))
                } else {
                    _6 = this.getAttributesSVG().split(/\s/);
                    for (var i = 0; i < _6.length; ++i) {
                        _7 = _6[i].split('=');
                        _2.setAttributeNS(null, _7[0], _7[1].replace(/'/g, ""))
                    }
                }
            }
            return _2
        }, getAttributesSVG: function() {
            return""
        }, $830: function(_1, _2) {
            var _3 = [], _4 = Math.abs(_2[2] - _2[0]), _5 = Math.abs(_2[3] - _2[1]);
            if (typeof(_1.direction) === 'number') {
                var _6 = Math.round(Math.sqrt(_4 * _4 + _5 * _5));
                var _7 = _1.direction * Math.PI / 180.0;
                _1.x1 = _2[0];
                _1.x2 = _2[0] + _6 * Math.cos(_7);
                _1.y1 = _2[1];
                _1.y2 = _2[1] + _6 * Math.sin(_7)
            }
            if (typeof(_1.x1) === 'string' && _1.x1.endsWith('%')) {
                _3.push(_2[0] + parseFloat(_1.x1) * _4 / 100.0)
            } else if (isc.isA.Number(_1.x1)) {
                _3.push(_1.x1)
            }
            if (typeof(_1.y1) === 'string' && _1.y1.endsWith('%')) {
                _3.push(_2[1] + parseFloat(_1.y1) * _5 / 100.0)
            } else if (isc.isA.Number(_1.y1)) {
                _3.push(_1.y1)
            }
            if (typeof(_1.x2) === 'string' && _1.x2.endsWith('%')) {
                _3.push(_2[0] + parseFloat(_1.x2) * _4 / 100.0)
            } else if (isc.isA.Number(_1.x2)) {
                _3.push(_1.x2)
            }
            if (typeof(_1.y2) === 'string' && _1.y2.endsWith('%')) {
                _3.push(_2[1] + parseFloat(_1.y2) * _5 / 100.0)
            } else if (isc.isA.Number(_1.y2)) {
                _3.push(_1.y2)
            }
            return _3
        }, $831: function(_1, _2) {
            var _3 = [], r;
            var _5 = this.getCenter();
            if (typeof(_1.fx) === 'string') {
                if (_1.fx.endsWith('%')) {
                    _3.push(_5[0] + (parseFloat(_1.fx) / 100.0))
                } else {
                    _3.push(_5[0] + parseFloat(_1.fx))
                }
            } else {
                _3.push(_5[0] + _1.fx)
            }
            if (typeof(_1.fy) === 'string') {
                if (_1.fy.endsWith('%')) {
                    _3.push(_5[1] + (parseFloat(_1.fy) / 100.0))
                } else {
                    _3.push(_5[1] + parseFloat(_1.fy))
                }
            } else {
                _3.push(_5[1] + _1.fy)
            }
            _3.push(0);
            if (typeof(_1.cx) === 'string' && isc.isA.Number(_2[0])) {
                if (_1.cx.endsWith('%')) {
                    _3.push(_2[0] - Math.round(_2[0] * parseInt(_1.cx) / 100.0))
                } else {
                    _3.push(_5[0] + parseInt(_1.cx))
                }
            } else {
                _3.push(_5[0] + _1.cx)
            }
            if (typeof(_1.cy) === 'string' && isc.isA.Number(_2[1])) {
                if (_1.cx.endsWith('%')) {
                    _3.push(_2[1] - Math.round(_2[1] * parseInt(_1.cy) / 100.0))
                } else {
                    _3.push(_5[1] + parseInt(_1.cy))
                }
            } else {
                _3.push(_5[1] + _1.cy)
            }
            if (typeof(_1.r) === 'string' && isc.isA.Number(_2[0]) && isc.isA.Number(_2[1])) {
                r = Math.sqrt(Math.pow(_2[2] - _2[0], 2) + Math.pow(_2[3] - _2[1], 2));
                if (_1.r.endsWith('%')) {
                    _3.push(r * parseFloat(_1.r) / 100.0)
                } else {
                    _3.push(r * parseFloat(_1.r))
                }
            } else {
                _3.push(_1.r)
            }
            return _3
        }, $85x: function(_1, _2, _3, _4, _5) {
            var _6 = [10, 5], _7 = _6.length, _8 = (_3 - _1), _9 = (_4 - _2);
            var _10 = _9 / _8;
            var _11 = Math.sqrt(_8 * _8 + _9 * _9);
            var _12 = 0, _13, _14, _15 = true;
            switch (this.linePattern.toLowerCase()) {
                default:
                case"dash":
                    _6 = [10, 10];
                    break;
                case"dot":
                    _6 = [1, 10];
                    break;
                case"longdash":
                    _6 = [20, 10];
                    break;
                case"shortdash":
                    _6 = [10, 5];
                    break;
                case"shortdot":
                    _6 = [1, 5];
                    break
            }
            var _16 = true;
            if (_3 < _1) {
                _16 = false
            }
            this.bmMoveTo(_1, _2, _5);
            while (_11 >= 0.1) {
                _13 = _6[_12++ % _7];
                if (_13 > _11) {
                    _13 = _11
                }
                _14 = Math.sqrt(_13 * _13 / (1 + _10 * _10));
                if (_16) {
                    _1 += _14
                } else {
                    _1 -= _14
                }
                _2 += _10 * _14;
                this[_15 ? 'bmLineTo' : 'bmMoveTo'](_1, _2, _5);
                _11 -= _13;
                _15 = !_15
            }
        }, drawStroke: function(_1) {
            _1.lineWidth = this.lineWidth;
            _1.lineCap = this.lineCap;
            _1.strokeStyle = this.lineColor;
            _1.globalAlpha = this.lineOpacity;
            _1.beginPath();
            this.drawBitmapPath(_1);
            _1.stroke()
        }, drawBitmap: function(_1) {
            _1.save();
            try {
                if (this.drawPane && (this.drawPane.zoomLevel || this.drawPane.translate || this.drawPane.rotation)) {
                    _1.setTransform(1, 0, 0, 1, 0, 0);
                    if (this.drawPane.translate) {
                        _1.translate(this.drawPane.translate[0], this.drawPane.translate[1])
                    }
                }
                if (this.translate) {
                    _1.translate(this.translate[0], this.translate[1])
                }
                if (this.scale && this.scale.length === 2) {
                    _1.scale(this.scale[0], this.scale[1])
                }
                if (this.rotation && !isc.isA.DrawLabel(this)) {
                    var _2 = this.getCenter && this.getCenter();
                    if (_2 && _2.length === 2) {
                        _1.translate(_2[0], _2[1]);
                        _1.rotate(this.rotation * Math.PI / 180.0);
                        _1.translate(-_2[0], -_2[1])
                    }
                }
                var _3 = this.fillColor, _4, _5, _6;
                if (this.fillGradient) {
                    _4 = typeof(this.fillGradient) === 'string' ? this.drawPane.gradients[this.fillGradient] : this.fillGradient;
                    if (typeof(_4.direction) === 'number' || _4.x1 || typeof(_4.x1) === 'number') {
                        _5 = this.$830(_4, this.getBoundingBox());
                        _3 = _1.createLinearGradient(_5[0], _5[1], _5[2], _5[3])
                    } else {
                        _5 = this.$831(_4, this.getBoundingBox());
                        _3 = _1.createRadialGradient(_5[0], _5[1], _5[2], _5[3], _5[4], _5[5])
                    }
                    if (_4.startColor && _4.endColor) {
                        _3.addColorStop(0.0, _4.startColor);
                        _3.addColorStop(1.0, _4.endColor)
                    } else if (_4.colorStops && _4.colorStops.length) {
                        for (var i = 0; i < _4.colorStops.length; ++i) {
                            _6 = _4.colorStops[i].offset;
                            if (typeof(_6) === 'string' && _6.endsWith('%')) {
                                _6 = parseFloat(_6.substring(0, _6.length - 1)) / 100.0
                            }
                            _3.addColorStop(_6, _4.colorStops[i].color)
                        }
                    }
                }
                if (this.shadow) {
                    _1.shadowColor = this.shadow.color;
                    _1.shadowBlur = this.shadow.blur;
                    _1.shadowOffsetX = this.shadow.offset[0];
                    _1.shadowOffsetY = this.shadow.offset[1]
                }
                if (_3) {
                    _1.fillStyle = _3;
                    _1.globalAlpha = this.fillOpacity;
                    _1.beginPath();
                    this.drawBitmapPath(_1);
                    _1.fill()
                }
                if (this.lineColor || this.shadow) {
                    this.drawStroke(_1)
                }
            } catch (e) {
                if (!isc.Log.supportsOnError)
                    this.$am(e);
                else
                    this.logWarn("exception during drawBitmap(): " + e)
            }
            _1.restore()
        }, $442: function() {
            if (this.$444) {
                if (this.startArrow == "block")
                    return this.$444;
                if (this.$44m.getElementById(this.startArrow)) {
                    this.$444 = this.startArrow;
                    this.$445 = null
                } else {
                    this.logWarn("SVG marker '" + this.startArrow + "' does not exist; ignoring")
                }
            } else {
                if (this.startArrow === "block" || !this.$44m.getElementById(this.startArrow)) {
                    this.$444 = "isc_DrawItem_" + this.drawItemID + "$446";
                    this.$445 = this.$447(this.$444, this.lineColor, this.lineOpacity, true)
                } else {
                    this.$444 = this.startArrow
                }
            }
            this.startArrow = "block";
            return this.$444
        }, $443: function() {
            if (this.$448) {
                if (this.endArrow == "block")
                    return this.$448;
                if (this.$44m.getElementById(this.endArrow)) {
                    this.$448 = this.endArrow;
                    this.$449 = null
                } else {
                    this.logWarn("SVG marker '" + this.endArrow + "' does not exist; ignoring")
                }
            } else {
                if (this.endArrow == "block" || !this.$44m.getElementById(this.endArrow)) {
                    this.$448 = "isc_DrawItem_" + this.drawItemID + "$45a";
                    this.$449 = this.$447(this.$448, this.lineColor, this.lineOpacity, false)
                } else {
                    this.$448 = this.endArrow
                }
            }
            this.endArrow = "block";
            return this.$448
        }, $447: function(_1, _2, _3, _4) {
            var _5;
            if (!isc.Browser.isWebKit) {
                _5 = "<marker id='" + _1 + "' viewBox='0 0 10 10' refY='5' refX='" + (_4 ? "10" : "0") + "' orient='auto' markerUnits='strokeWidth' markerWidth='4' markerHeight='3'><path d='" + (_4 ? "M 10 0 L 0 5 L 10 10 z" : "M 0 0 L 10 5 L 0 10 z") + "' fill='" + ((_2 && _2 != "") ? _2 : "none") + "' fill-opacity='" + _3 + "' stroke-width='0px'/></marker>";
                var _6 = this.$44m.createRange();
                _6.setStart(this.$44o, 0);
                this.$44o.appendChild(_6.createContextualFragment(_5))
            } else {
                _5 = this.$44m.createElementNS("http://www.w3.org/2000/svg", "marker");
                _5.setAttributeNS(null, "id", _1);
                _5.setAttributeNS(null, "viewBox", "0 0 10 10");
                _5.setAttributeNS(null, "refY", "5");
                _5.setAttributeNS(null, "refX", (_4 ? "10" : "0"));
                _5.setAttributeNS(null, "orient", "auto");
                _5.setAttributeNS(null, "markerUnits", "strokeWidth");
                _5.setAttributeNS(null, "markerWidth", "4");
                _5.setAttributeNS(null, "markerHeight", "3");
                var _7 = this.$44m.createElementNS("http://www.w3.org/2000/svg", "path");
                _7.setAttributeNS(null, "d", (_4 ? "M 10 0 L 0 5 L 10 10 z" : "M 0 0 L 10 5 L 0 10 z"));
                _7.setAttributeNS(null, "fill", ((_2 && _2 != "") ? _2 : "none"));
                _7.setAttributeNS(null, "fillOpacity", _3);
                _7.setAttributeNS(null, "strokeWidth", "0px");
                _5.appendChild(_7);
                this.$44o.appendChild(_5)
            }
            return this.$44o.lastChild.firstChild
        }, show: function() {
            this.hidden = false;
            if (this.drawingVML) {
                this.$44u.style.visibility = "visible"
            } else if (this.drawingSVG) {
                this.$44z.setAttributeNS(null, "visibility", "visible")
            } else if (this.drawingBitmap) {
                this.drawPane.redrawBitmap()
            }
        }, hide: function() {
            this.hidden = true;
            if (this.drawingVML) {
                this.$44u.style.visibility = "hidden"
            } else if (this.drawingSVG) {
                this.$44z.setAttributeNS(null, "visibility", "hidden")
            } else if (this.drawingBitmap) {
                this.drawPane.redrawBitmap()
            }
        }, setLineWidth: function(_1) {
            if (_1 != null)
                this.lineWidth = _1;
            if (this.drawingVML) {
                this.$44l(this.lineWidth * this.drawPane.zoomLevel)
            } else if (this.drawingSVG) {
                this.$44z.setAttributeNS(null, "stroke-width", this.lineWidth + "px");
                this.$44z.setAttributeNS(null, "stroke-dasharray", this.$441())
            } else if (this.drawingBitmap) {
                this.drawPane.redrawBitmap()
            }
        }, $44l: function(_1) {
            if (this.$44w) {
                this.$44w.weight = _1 + "px"
            }
        }, setLineColor: function(_1) {
            this.lineColor = _1;
            if (this.drawingVML) {
                if (this.lineColor && this.lineColor != "") {
                    this.$44w.on = true;
                    this.$44w.color = this.lineColor
                } else {
                    this.$44w.on = false
                }
            } else if (this.drawingSVG) {
                var _1 = (this.lineColor && this.lineColor != "") ? this.lineColor : "none"
                this.$44z.setAttributeNS(null, "stroke", _1);
                if (this.$445) {
                    this.$445.setAttributeNS(null, "stroke", _1);
                    this.$445.setAttributeNS(null, "fill", _1)
                }
                if (this.$449) {
                    this.$449.setAttributeNS(null, "stroke", _1);
                    this.$449.setAttributeNS(null, "fill", _1)
                }
            } else if (this.drawingBitmap) {
                this.drawPane.redrawBitmap()
            }
        }, setLineOpacity: function(_1) {
            if (_1 != null)
                this.lineOpacity = _1;
            if (this.drawingVML) {
                this.$44w.opacity = this.lineOpacity
            } else if (this.drawingSVG) {
                this.$44z.setAttributeNS(null, "stroke-opacity", this.lineOpacity);
                if (this.$445) {
                    this.$445.setAttributeNS(null, "stroke-opacity", this.lineOpacity);
                    this.$445.setAttributeNS(null, "fill-opacity", this.lineOpacity)
                }
                if (this.$449) {
                    this.$449.setAttributeNS(null, "stroke-opacity", this.lineOpacity);
                    this.$449.setAttributeNS(null, "fill-opacity", this.lineOpacity)
                }
            } else if (this.drawingBitmap) {
                this.drawPane.redrawBitmap()
            }
        }, setLinePattern: function(_1) {
            this.linePattern = _1;
            if (this.drawingVML) {
                this.$44w.dashstyle = this.linePattern
            } else if (this.drawingSVG) {
                this.$44z.setAttributeNS(null, "stroke-dasharray", this.$441())
            } else if (this.drawingBitmap) {
            }
        }, $44r: {shortdot: 1, dot: 1, shortdash: 3, dash: 4, longdash: 8}, $44s: {shortdot: 1, dot: 3, shortdash: 1, dash: 3, longdash: 3}, $441: function() {
            var _1 = this.linePattern;
            if (!_1 || _1 == "solid" || _1 == "")
                return"none";
            if (isc.isAn.Array(_1))
                return _1.join("px,") + "px";
            var _2 = this.$44r[_1];
            var _3 = this.$44s[_1];
            if (this.lineCap != "butt") {
                _2 = Math.max(0.1, _2 - 1);
                _3++
            }
            return(_2 * this.lineWidth) + "px," + (_3 * this.lineWidth) + "px"
        }, setLineCap: function(_1) {
            if (_1 != null)
                this.lineCap = _1;
            if (this.drawingVML) {
                this.$44w.endcap = (this.lineCap == "butt") ? "flat" : this.lineCap
            } else if (this.drawingSVG) {
                this.$44z.setAttributeNS(null, "stroke-linecap", this.lineCap);
                this.$44z.setAttributeNS(null, "stroke-dasharray", this.$441())
            } else if (this.drawingBitmap) {
                this.drawPane.redrawBitmap()
            }
        }, setShadow: function(_1) {
            if (_1 != null) {
                this.shadow = _1
            }
            if (this.drawingVML) {
            } else if (this.drawingSVG) {
            } else if (this.drawingBitmap) {
                this.drawPane.redrawBitmap()
            }
        }, setStartArrow: function(_1) {
            var _2 = this.$63r();
            var _3;
            if (_1 !== _3)
                this.startArrow = _1;
            else
                _1 = this.startArrow;
            var _4 = this.$63r();
            if (_2 != _4) {
                this.setStartPoint()
            }
            if (_4)
                _1 = null;
            if (this.drawingVML) {
                this.$44w.startarrow = (_1 ? _1 : "none")
            } else if (this.drawingSVG) {
                this.$44z.setAttributeNS(null, "marker-start", (_1 ? "url(#" + this.$442() + ")" : "none"))
            } else if (this.drawingBitmap) {
            }
        }, setEndArrow: function(_1) {
            var _2 = this.$63s();
            var _3;
            if (_1 !== _3)
                this.endArrow = _1;
            else
                _1 = this.endArrow;
            var _4 = this.$63s();
            if (_2 != _4) {
                this.setEndPoint()
            }
            if (_4)
                _1 = null;
            if (this.drawingVML) {
                this.$44w.endarrow = (_1 ? _1 : "none")
            } else if (this.drawingSVG) {
                this.$44z.setAttributeNS(null, "marker-end", (_1 ? "url(#" + this.$443() + ")" : "none"))
            } else if (this.drawingBitmap) {
            }
        }, moveBy: function(_1, _2) {
            if (this.item) {
                var _3 = this.getBoundingBox();
                this.item.x = _3[0];
                this.item.y = _3[1];
                this.item.width = Math.abs(_3[2] - _3[0]);
                this.item.height = Math.abs(_3[3] - _3[1]);
                this.drawPane.updateQuadTree(this)
            }
        }, resizeBy: function(_1, _2) {
            if (this.item) {
                var _3 = this.getBoundingBox();
                this.item.x = _3[0];
                this.item.y = _3[1];
                this.item.width = Math.abs(_3[2] - _3[0]);
                this.item.height = Math.abs(_3[3] - _3[1]);
                this.drawPane.updateQuadTree(this)
            }
        }, rotateBy: function(_1) {
            this.rotation += _1;
            if (this.drawingVML) {
                this.$44u.style.rotation = this.rotation
            } else if (this.drawingSVG) {
                var _2 = this.getCenter();
                this.$44z.setAttributeNS(null, "transform", "translate(" + _2[0] + "," + _2[1] + ") rotate(" + this.rotation + ") translate(" + -_2[0] + "," + -_2[1] + ")")
            } else if (this.drawingBitmap) {
                this.drawPane.redrawBitmap()
            }
            this.rotated(_1)
        }, rotateTo: function(_1) {
            this.rotateBy(_1 - this.rotation)
        }, scaleBy: function(_1, _2) {
            this.scale = this.scale || [];
            this.scale[0] = _1;
            this.scale[1] = _2;
            if (this.drawingVML) {
            } else if (this.drawingSVG) {
                var _3 = "scale(" + this.scale[0] + "," + this.scale[1] + ") " + (this.$44z.getAttributeNS(null, "transform") || "");
                this.$44z.setAttributeNS(null, "transform", _3)
            } else if (this.drawingBitmap) {
                this.drawPane.redrawBitmap()
            }
            this.scaled()
        }, scaleTo: function(_1, _2) {
            this.scaleBy(_1, _2)
        }, setFillColor: function(_1) {
            this.fillColor = _1;
            if (this.drawingVML) {
                if (this.fillColor && this.fillColor != "") {
                    this.$44x.on = true;
                    this.$44x.color = this.fillColor
                } else {
                    this.$44x.on = false
                }
            } else if (this.drawingSVG) {
                this.$44z.setAttributeNS(null, "fill", (this.fillColor && this.fillColor != "") ? this.fillColor : "none")
            } else if (this.drawingBitmap) {
                this.drawPane.redrawBitmap()
            }
        }, setFillGradient: function(_1) {
            this.gradient = _1;
            if (this.drawingSVG) {
                this.$44z.setAttributeNS(null, "fill", (this.fillColor && this.fillColor != "") ? this.fillColor : "none")
            } else if (this.drawingBitmap) {
                this.drawPane.redrawBitmap()
            }
        }, setFillOpacity: function(_1) {
            if (_1 != null)
                this.fillOpacity = _1;
            if (this.drawingVML) {
                this.$44x.opacity = this.fillOpacity
            } else if (this.drawingSVG) {
                this.$44z.setAttributeNS(null, "fill-opacity", this.fillOpacity)
            } else if (this.drawingBitmap) {
                this.drawPane.redrawBitmap()
            }
        }, bmMoveTo: function(_1, _2, _3) {
            var _3 = _3 || this.drawPane.getBitmapContext();
            var _4 = (this.autoOffset && (this.lineWidth == 0 || parseInt(this.lineWidth) % 2) == 1 ? 0.5 : 0);
            try {
                _3.moveTo(_1 + _4, _2 + _4)
            } catch (e) {
                this.logWarn("error on moveTo(): left: " + _1 + ", top: " + _2 + (this.logIsInfoEnabled() ? this.getStackTrace() : ""))
            }
        }, bmArc: function(_1, _2, _3, _4, _5, _6) {
            var _6 = _6 || this.drawPane.getBitmapContext();
            var _7 = (this.autoOffset && (this.lineWidth == 0 || parseInt(this.lineWidth) % 2) == 1 ? 0.5 : 0);
            try {
                _6.arc(_1 + _7, _2 + _7, _3, _4, _5, false)
            } catch (e) {
                this.logWarn("error on lineTo(): left: " + left + ", top: " + top + (this.logIsInfoEnabled() ? this.getStackTrace() : ""))
            }
        }, bmLineTo: function(_1, _2, _3) {
            var _3 = _3 || this.drawPane.getBitmapContext();
            var _4 = (this.autoOffset && (this.lineWidth == 0 || parseInt(this.lineWidth) % 2) == 1 ? 0.5 : 0);
            try {
                _3.lineTo(_1 + _4, _2 + _4)
            } catch (e) {
                this.logWarn("error on lineTo(): left: " + _1 + ", top: " + _2 + (this.logIsInfoEnabled() ? this.getStackTrace() : ""))
            }
        }, bmQuadraticCurveTo: function(_1, _2, _3, _4) {
            var _5 = _5 || this.drawPane.getBitmapContext();
            var _6 = (this.autoOffset && (this.lineWidth == 0 || parseInt(this.lineWidth) % 2) == 1 ? 0.5 : 0);
            try {
                _5.quadraticCurveTo(_1 + _6, _2 + _6, _3 + _6, _4 + _6)
            } catch (e) {
                this.logWarn("error on quadraticCurveTo(): left: " + left + ", top: " + top + (this.logIsInfoEnabled() ? this.getStackTrace() : ""))
            }
        }, bmTranslate: function(_1, _2, _3) {
            var _3 = _3 || this.drawPane.getBitmapContext();
            try {
                _3.translate(_1, _2)
            } catch (e) {
                this.logWarn("error on translate(): left: " + _1 + ", top: " + _2 + (this.logIsInfoEnabled() ? this.getStackTrace() : ""))
            }
        }, bmRotate: function(_1, _2) {
            var _2 = _2 || this.drawPane.getBitmapContext();
            try {
                _2.rotate(_1)
            } catch (e) {
                this.logWarn("error on translate(): angle: " + _1 + (this.logIsInfoEnabled() ? this.getStackTrace() : ""))
            }
        }, bmFillText: function(_1, _2, _3, _4) {
            var _4 = _4 || this.drawPane.getBitmapContext();
            try {
                _4.fillText(_1, _2, _3)
            } catch (e) {
                this.logWarn("error on fillText(): text: " + _1 + ", left: " + _2 + ", top: " + _3 + (this.logIsInfoEnabled() ? this.getStackTrace() : ""))
            }
        }, bmStrokeText: function(_1, _2, _3, _4) {
            var _4 = _4 || this.drawPane.getBitmapContext();
            try {
                _4.strokeText(_1, _2, _3)
            } catch (e) {
                this.logWarn("error on fillText(): text: " + _1 + ", left: " + _2 + ", top: " + _3 + (this.logIsInfoEnabled() ? this.getStackTrace() : ""))
            }
        }})
    isc.A = isc.DrawItem;
    isc.B = isc._allFuncs;
    isc.C = isc.B._maxIndex;
    isc.D = isc._funcClasses;
    isc.D[isc.C] = isc.A.Class;
    isc.A.$44q = 0;
    isc.A.$630 = ["show", , "Knobs"];
    isc.A.$631 = ["hide", , "Knobs"];
    isc.B.push(isc.A.$632 = function isc_c_DrawItem__getShowKnobsFunctionName(_1, _2) {
        if (!this.$633) {
            this.$633 = {}
        }
        if (this.$633[_1] == null) {
            _1 = _1.substring(0, 1).toUpperCase() + _1.substring(1);
            this.$630[1] = _1;
            this.$631[1] = _1;
            this.$633[_1] = [this.$630.join(isc.emptyString), this.$631.join(isc.emptyString)]
        }
        return _2 ? this.$633[_1][1] : this.$633[_1][0]
    }
    );
    isc.B._maxIndex = isc.C + 1;
    isc.defineClass("DrawGroup", "DrawItem");
    isc.A = isc.DrawGroup.getPrototype();
    isc.B = isc._allFuncs;
    isc.C = isc.B._maxIndex;
    isc.D = isc._funcClasses;
    isc.D[isc.C] = isc.A.Class;
    isc.A.left = 0;
    isc.A.top = 0;
    isc.B.push(isc.A.init = function isc_DrawGroup_init() {
        if (!this.drawItems)
            this.drawItems = [];
        this.Super("init")
    }
    , isc.A.erase = function isc_DrawGroup_erase(_1) {
        for (var i = 0; i < this.drawItems.length; i++) {
            this.drawItems[i].erase(true)
        }
        this.drawItems = [];
        this.Super("erase", arguments)
    }
    , isc.A.$44v = function isc_DrawGroup__getElementVML(_1) {
        return isc.SB.concat(this.drawPane.startTagVML('FOO'), " onmousedown='return ", this.drawPane.getID(), ".mouseDown();' ", " ID='", _1, "' ", this.getAttributesVML(), ">", this.drawPane.endTagVML(this.vmlElementName))
    }
    , isc.A.$440 = function isc_DrawGroup__getElementSVG(_1) {
        var _2;
        if (!isc.Browser.isWebKit) {
            return"<g id='" + _1 + "' transform='translate(" + this.left + "," + this.top + ")'></g>"
        } else {
            _2 = this.$44m.createElementNS("http://www.w3.org/2000/svg", "g");
            _2.setAttributeNS(null, "id", _1);
            var _3 = "translate(" + this.left + "," + this.top + ")";
            _2.setAttributeNS(null, "transform", _3)
        }
        return _2
    }
    , isc.A.drawBitmap = function isc_DrawGroup_drawBitmap(_1) {
        _1.save();
        _1.translate(this.left, this.top);
        for (var i = 0; i < this.drawItems.length; i++) {
            if (!this.drawItems[i].hidden)
                this.drawItems[i].drawBitmap(_1)
        }
        _1.restore()
    }
    , isc.A.drawBitmapPath = function isc_DrawGroup_drawBitmapPath(_1) {
        this.bmMoveTo(this.left, this.top);
        this.bmLineTo(this.left + this.width, this.top);
        this.bmLineTo(this.left + this.width, this.top + this.height);
        this.bmLineTo(this.left, this.top + this.height);
        this.bmLineTo(this.left, this.top)
    }
    , isc.A.setLineWidth = function isc_DrawGroup_setLineWidth(_1) {
        this.logWarn("no setLineWidth")
    }
    , isc.A.$44l = function isc_DrawGroup__setLineWidthVML(_1) {
        this.logWarn("no $44l")
    }
    , isc.A.setLineColor = function isc_DrawGroup_setLineColor(_1) {
        this.logWarn("no setLineColor")
    }
    , isc.A.setLineOpacity = function isc_DrawGroup_setLineOpacity(_1) {
        this.logWarn("no setLineOpacity")
    }
    , isc.A.setLinePattern = function isc_DrawGroup_setLinePattern(_1) {
        this.logWarn("no setLinePattern")
    }
    , isc.A.setLineCap = function isc_DrawGroup_setLineCap(_1) {
        this.logWarn("no setLineCap")
    }
    , isc.A.setStartArrow = function isc_DrawGroup_setStartArrow(_1) {
        this.logWarn("no setStartArrow")
    }
    , isc.A.setEndArrow = function isc_DrawGroup_setEndArrow(_1) {
        this.logWarn("no setEndArrow")
    }
    , isc.A.setFillColor = function isc_DrawGroup_setFillColor(_1) {
        this.logWarn("no setFillColor")
    }
    , isc.A.setFillOpacity = function isc_DrawGroup_setFillOpacity(_1) {
        this.logWarn("no setFillOpacity")
    }
    , isc.A.setLeft = function isc_DrawGroup_setLeft(_1) {
        if (_1 != null)
            this.left = _1;
        if (this.drawingVML) {
            this.$44u.coordorigin.x = -this.left
        } else if (this.drawingSVG) {
            var _2 = "translate(" + this.left + "," + this.top + ") " + (this.$44z.getAttributeNS(null, "transform") || "");
            this.$44z.setAttributeNS(null, "transform", _2)
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
    }
    , isc.A.setTop = function isc_DrawGroup_setTop(_1) {
        if (_1 != null)
            this.top = _1;
        if (this.drawingVML) {
            this.$44u.coordorigin.y = -this.top
        } else if (this.drawingSVG) {
            var _2 = "translate(" + this.left + "," + this.top + ") " + (this.$44z.getAttributeNS(null, "transform") || "");
            this.$44z.setAttributeNS(null, "transform", _2)
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
    }
    , isc.A.moveTo = function isc_DrawGroup_moveTo(_1, _2) {
        this.moveBy(_1 - this.left, _2 - this.top)
    }
    , isc.A.moveBy = function isc_DrawGroup_moveBy(_1, _2) {
        this.left += _1;
        this.top += _2;
        for (var i = 0; i < this.drawItems.length; ++i) {
            var _4 = this.drawItems[i];
            _4.moveBy(_1, _2)
        }
        this.moved(_1, _2)
    }
    , isc.A.rotateTo = function isc_DrawGroup_rotateTo(_1) {
        this.rotateBy(_1 - this.rotation)
    }
    , isc.A.rotateBy = function isc_DrawGroup_rotateBy(_1) {
        this.rotation += _1;
        for (var i = 0; i < this.drawItems.length; ++i) {
            var _3 = this.drawItems[i];
            _3.rotateBy(_1)
        }
    }
    , isc.A.scaleBy = function isc_DrawGroup_scaleBy(_1, _2) {
        for (var i = 0; i < this.drawItems.length; ++i) {
            var _4 = this.drawItems[i];
            _4.scaleBy(_1, _2)
        }
    }
    , isc.A.getCenter = function isc_DrawGroup_getCenter() {
        return[this.left + Math.round(this.width / 2), this.top + Math.round(this.height / 2)]
    }
    , isc.A.getBoundingBox = function isc_DrawGroup_getBoundingBox() {
        return[this.left, this.top, this.left + this.width, this.top + this.height]
    }
    , isc.A.scaleTo = function isc_DrawGroup_scaleTo(_1, _2) {
        this.scale = this.scale || [];
        this.scale[0] += (this.scale[0] || 0) + _1;
        this.scale[1] += (this.scale[1] || 0) + _2;
        this.scaleBy(_1, _2)
    }
    , isc.A.dragStart = function isc_DrawGroup_dragStart(_1, _2) {
        var _3 = this.getBoundingBox();
        var _4 = _3[0];
        var _5 = _3[1];
        var _6 = this.drawPane.normalize(_1.x, _1.y);
        this.dragOffsetX = _6.x - this.drawPane.getPageLeft() - _4;
        this.dragOffsetY = _6.y - this.drawPane.getPageTop() - _5;
        for (var i = 0; i < this.drawItems.length; ++i) {
            var _8 = this.drawItems[i];
            _8.dragStart(_1, _2)
        }
        return true
    }
    , isc.A.dragMove = function isc_DrawGroup_dragMove(_1, _2) {
        var _3 = this.drawPane.normalize(_1.x, _1.y);
        var x = _3.x - this.drawPane.getPageLeft() - this.dragOffsetX;
        var y = _3.y - this.drawPane.getPageTop() - this.dragOffsetY;
        this.moveTo(x, y);
        for (var i = 0; i < this.drawItems.length; ++i) {
            var _7 = this.drawItems[i];
            _7.dragMove(_1, _2)
        }
        return true
    }
    , isc.A.dragStop = function isc_DrawGroup_dragStop(_1, _2) {
        var _3 = this.drawPane.normalize(_1.x, _1.y);
        var x = _3.x - this.drawPane.getPageLeft() - this.dragOffsetX;
        var y = _3.y - this.drawPane.getPageTop() - this.dragOffsetY;
        this.moveTo(x, y);
        for (var i = 0; i < this.drawItems.length; ++i) {
            var _7 = this.drawItems[i];
            _7.dragStop(_1, _2)
        }
        return true
    }
    , isc.A.click = function isc_DrawGroup_click() {
        for (var i = 0; i < this.drawItems.length; ++i) {
            var _2 = this.drawItems[i];
            _2.click()
        }
        return true
    }
    , isc.A.mouseDown = function isc_DrawGroup_mouseDown() {
        for (var i = 0; i < this.drawItems.length; ++i) {
            var _2 = this.drawItems[i];
            _2.mouseDown()
        }
        return true
    }
    , isc.A.mouseUp = function isc_DrawGroup_mouseUp() {
        for (var i = 0; i < this.drawItems.length; ++i) {
            var _2 = this.drawItems[i];
            _2.mouseUp()
        }
        return true
    }
    , isc.A.mouseMove = function isc_DrawGroup_mouseMove() {
        for (var i = 0; i < this.drawItems.length; ++i) {
            var _2 = this.drawItems[i];
            _2.mouseMove()
        }
        return true
    }
    , isc.A.mouseOut = function isc_DrawGroup_mouseOut() {
        for (var i = 0; i < this.drawItems.length; ++i) {
            var _2 = this.drawItems[i];
            _2.mouseOut()
        }
        return true
    }
    , isc.A.mouseOver = function isc_DrawGroup_mouseOver() {
        for (var i = 0; i < this.drawItems.length; ++i) {
            var _2 = this.drawItems[i];
            _2.mouseOver()
        }
        return true
    }
    );
    isc.B._maxIndex = isc.C + 35;
    isc.defineClass("DrawLine", "DrawItem");
    isc.A = isc.DrawLine.getPrototype();
    isc.B = isc._allFuncs;
    isc.C = isc.B._maxIndex;
    isc.D = isc._funcClasses;
    isc.D[isc.C] = isc.A.Class;
    isc.A.startPoint = [0, 0];
    isc.A.endPoint = [100, 100];
    isc.A.svgElementName = "line";
    isc.A.vmlElementName = "LINE";
    isc.B.push(isc.A.init = function isc_DrawLine_init() {
        this.startLeft = this.startLeft || this.startPoint[0];
        this.startTop = this.startTop || this.startPoint[1];
        this.endLeft = this.endLeft || this.endPoint[0];
        this.endTop = this.endTop || this.endPoint[1];
        this.Super("init")
    }
    , isc.A.$45b = function isc_DrawLine__getArrowAdjustedPoints() {
        if (this.startArrow || this.endArrow) {
            return isc.GraphMath.trimLineEnds(this.startLeft, this.startTop, this.endLeft, this.endTop, this.startArrow ? this.lineWidth * 3 : 0, this.endArrow ? this.lineWidth * 3 : 0)
        } else {
            return[this.startLeft, this.startTop, this.endLeft, this.endTop]
        }
    }
    , isc.A.setLineWidth = function isc_DrawLine_setLineWidth(_1) {
        this.Super("setLineWidth", arguments);
        if (this.drawingSVG)
            this.setStartPoint()
    }
    , isc.A.setStartArrow = function isc_DrawLine_setStartArrow(_1) {
        this.Super("setStartArrow", arguments);
        if (this.drawingSVG)
            this.setStartPoint()
    }
    , isc.A.setEndArrow = function isc_DrawLine_setEndArrow(_1) {
        this.Super("setEndArrow", arguments);
        if (this.drawingSVG)
            this.setEndPoint()
    }
    , isc.A.getAttributesVML = function isc_DrawLine_getAttributesVML() {
        return isc.SB.concat("FROM='", this.startLeft, " ", this.startTop, "' TO='", this.endLeft, " ", this.endTop, "'")
    }
    , isc.A.getAttributesSVG = function isc_DrawLine_getAttributesSVG() {
        var _1 = this.$45b();
        return"x1='" + _1[0] + "' y1='" + _1[1] + "' x2='" + _1[2] + "' y2='" + _1[3] + "'"
    }
    , isc.A.drawBitmapPath = function isc_DrawLine_drawBitmapPath(_1) {
        var _2 = (this.lineWidth == 0 || parseInt(this.lineWidth) % 2) == 1 ? 0.5 : 0, _3 = parseFloat(this.startLeft) + _2, _4 = parseFloat(this.startTop) + _2, _5 = parseFloat(this.endLeft) + _2, _6 = parseFloat(this.endTop) + _2;
        if (this.linePattern.toLowerCase() !== "solid") {
            this.$85x(_3, _4, _5, _6, _1)
        } else {
            this.bmMoveTo(this.startLeft, this.startTop, _1);
            this.bmLineTo(this.endLeft, this.endTop, _1)
        }
    }
    , isc.A.setStartPoint = function isc_DrawLine_setStartPoint(_1, _2) {
        if (_1 != null)
            this.startLeft = _1;
        if (_2 != null)
            this.startTop = _2;
        if (this.drawingVML) {
            this.$44u.from = this.startLeft + " " + this.startTop
        } else if (this.drawingSVG) {
            var _3 = this.$45b();
            this.$44z.setAttributeNS(null, "x1", _3[0]);
            this.$44z.setAttributeNS(null, "y1", _3[1]);
            if (this.endArrow) {
                this.$44z.setAttributeNS(null, "x2", _3[2]);
                this.$44z.setAttributeNS(null, "y2", _3[3])
            }
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
        this.updateControlKnobs()
    }
    , isc.A.setEndPoint = function isc_DrawLine_setEndPoint(_1, _2) {
        if (_1 != null)
            this.endLeft = _1;
        if (_2 != null)
            this.endTop = _2;
        if (this.drawingVML) {
            this.$44u.to = this.endLeft + " " + this.endTop
        } else if (this.drawingSVG) {
            var _3 = this.$45b();
            this.$44z.setAttributeNS(null, "x2", _3[2]);
            this.$44z.setAttributeNS(null, "y2", _3[3]);
            if (this.startArrow) {
                this.$44z.setAttributeNS(null, "x1", _3[0]);
                this.$44z.setAttributeNS(null, "y1", _3[1])
            }
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
        this.updateControlKnobs()
    }
    , isc.A.getBoundingBox = function isc_DrawLine_getBoundingBox() {
        return[].concat(this.startPoint, this.endPoint)
    }
    , isc.A.getCenter = function isc_DrawLine_getCenter() {
        return[this.startPoint[0] + Math.round((this.endPoint[0] - this.startPoint[0]) / 2), this.startPoint[1] + Math.round((this.endPoint[1] - this.startPoint[1]) / 2)]
    }
    , isc.A.showStartPointKnobs = function isc_DrawLine_showStartPointKnobs() {
        this.$639 = this.createAutoChild("startKnob", {_constructor: "DrawKnob", x: this.startLeft, y: this.startTop, drawPane: this.drawPane, autoDraw: true});
        this.observe(this.$639, "updatePoints", "observer.setStartPoint(x,y)")
    }
    , isc.A.hideStartPointKnobs = function isc_DrawLine_hideStartPointKnobs() {
        if (this.$639) {
            this.$639.destroy();
            delete this.$639
        }
    }
    , isc.A.showEndPointKnobs = function isc_DrawLine_showEndPointKnobs() {
        this.$64a = this.createAutoChild("endKnob", {_constructor: "DrawKnob", x: this.endLeft, y: this.endTop, drawPane: this.drawPane, autoDraw: true});
        this.observe(this.$64a, "updatePoints", "observer.setEndPoint(x,y)")
    }
    , isc.A.hideEndPointKnobs = function isc_DrawLine_hideEndPointKnobs() {
        if (this.$64a) {
            this.$64a.destroy();
            delete this.$64a
        }
    }
    , isc.A.moveBy = function isc_DrawLine_moveBy(_1, _2) {
        this.startLeft += _1;
        this.startTop += _2;
        this.endLeft += _1;
        this.endTop += _2;
        this.startPoint[0] = this.startLeft;
        this.startPoint[1] = this.startTop;
        this.endPoint[0] = this.endLeft;
        this.endPoint[1] = this.endTop;
        if (this.drawingVML) {
            this.$44u.from = this.startLeft + " " + this.startTop;
            this.$44u.to = this.endLeft + " " + this.endTop
        } else if (this.drawingSVG) {
            var _3 = this.$45b();
            this.$44z.setAttributeNS(null, "x1", _3[0]);
            this.$44z.setAttributeNS(null, "y1", _3[1]);
            this.$44z.setAttributeNS(null, "x2", _3[2]);
            this.$44z.setAttributeNS(null, "y2", _3[3])
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
        this.Super("moveBy", arguments)
    }
    , isc.A.moveTo = function isc_DrawLine_moveTo(_1, _2) {
        this.moveBy(_1 - this.startLeft, _2 - this.startTop)
    }
    , isc.A.updateControlKnobs = function isc_DrawLine_updateControlKnobs() {
        this.Super("updateControlKnobs", arguments);
        if (this.$639) {
            var _1 = this.startLeft, _2 = this.startTop, _3 = this.drawPane.drawing2screen([_1, _2, 0, 0]);
            this.$639.setCenterPoint(_3[0], _3[1])
        }
        if (this.$64a) {
            var _1 = this.endLeft, _2 = this.endTop, _3 = this.drawPane.drawing2screen([_1, _2, 0, 0]);
            this.$64a.setCenterPoint(_3[0], _3[1])
        }
    }
    );
    isc.B._maxIndex = isc.C + 19;
    isc.defineClass("DrawRect", "DrawItem");
    isc.A = isc.DrawRect.getPrototype();
    isc.B = isc._allFuncs;
    isc.C = isc.B._maxIndex;
    isc.D = isc._funcClasses;
    isc.D[isc.C] = isc.A.Class;
    isc.A.left = 0;
    isc.A.top = 0;
    isc.A.width = 100;
    isc.A.height = 100;
    isc.A.rounding = 0;
    isc.A.lineCap = "butt";
    isc.A.svgElementName = "rect";
    isc.A.vmlElementName = "ROUNDRECT";
    isc.B.push(isc.A.getAttributesVML = function isc_DrawRect_getAttributesVML() {
        return isc.SB.concat("STYLE='position:absolute; left:", this.left, "px; top:", this.top, "px; width:", this.width, "px; height:", this.height, "px;'", " ARCSIZE='" + this.rounding / 2 + "'")
    }
    , isc.A.getAttributesSVG = function isc_DrawRect_getAttributesSVG() {
        return"x='" + this.left + "' y='" + this.top + "' width='" + this.width + "' height='" + this.height + "'" + (this.rounding ? " rx='" + (this.rounding * Math.min(this.width, this.height) / 2) + "'" : "")
    }
    , isc.A.drawBitmapPath = function isc_DrawRect_drawBitmapPath(_1) {
        if (this.rounding == 0) {
            this.bmMoveTo(this.left, this.top);
            this.bmLineTo(this.left + this.width, this.top);
            this.bmLineTo(this.left + this.width, this.top + this.height);
            this.bmLineTo(this.left, this.top + this.height);
            this.bmLineTo(this.left, this.top)
        } else {
            var r = this.rounding * (Math.min(this.width, this.height) / 2);
            this.bmMoveTo(this.left + r, this.top);
            this.bmLineTo(this.left + this.width - r, this.top);
            this.bmQuadraticCurveTo(this.left + this.width, this.top, this.left + this.width, this.top + r);
            this.bmLineTo(this.left + this.width, this.top + this.height - r);
            this.bmQuadraticCurveTo(this.left + this.width, this.top + this.height, this.left + this.width - r, this.top + this.height);
            this.bmLineTo(this.left + r, this.top + this.height);
            this.bmQuadraticCurveTo(this.left, this.top + this.height, this.left, this.top + this.height - r);
            this.bmLineTo(this.left, this.top + r);
            this.bmQuadraticCurveTo(this.left, this.top, this.left + r, this.top)
        }
        _1.closePath()
    }
    , isc.A.setCenter = function isc_DrawRect_setCenter(_1, _2) {
        if (_1 != null)
            _1 = _1 - Math.round(this.width / 2);
        if (_2 != null)
            _2 = _2 - Math.round(this.height / 2);
        this.moveTo(_1, _2)
    }
    , isc.A.getCenter = function isc_DrawRect_getCenter() {
        return[this.left + Math.round(this.width / 2), this.top + Math.round(this.height / 2)]
    }
    , isc.A.getBoundingBox = function isc_DrawRect_getBoundingBox() {
        return[this.left, this.top, this.left + this.width, this.top + this.height]
    }
    , isc.A.moveBy = function isc_DrawRect_moveBy(_1, _2) {
        this.left += _1;
        this.top += _2;
        if (this.drawingVML) {
            this.$44u.style.left = this.left;
            this.$44u.style.top = this.top
        } else if (this.drawingSVG) {
            this.$44z.setAttributeNS(null, "x", this.left);
            this.$44z.setAttributeNS(null, "y", this.top)
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
        this.Super("moveBy", arguments);
        this.moved(_1, _2)
    }
    , isc.A.moveTo = function isc_DrawRect_moveTo(_1, _2) {
        this.moveBy(_1 - this.left, _2 - this.top)
    }
    , isc.A.setLeft = function isc_DrawRect_setLeft(_1) {
        this.moveTo(_1)
    }
    , isc.A.setTop = function isc_DrawRect_setTop(_1) {
        this.moveTo(null, _1)
    }
    , isc.A.resizeTo = function isc_DrawRect_resizeTo(_1, _2) {
        var _3 = 0, _4 = 0;
        if (_1 != null)
            _3 = _1 - this.width;
        if (_2 != null)
            _4 = _2 - this.height;
        this.resizeBy(_3, _4)
    }
    , isc.A.resizeBy = function isc_DrawRect_resizeBy(_1, _2) {
        if (_1 != null)
            this.width += _1;
        if (_2 != null)
            this.height += _2;
        if (this.drawingVML) {
            this.$44u.style.width = this.width;
            this.$44u.style.height = this.height
        } else if (this.drawingSVG) {
            this.$44z.setAttributeNS(null, "width", this.width);
            this.$44z.setAttributeNS(null, "height", this.height)
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
        this.Super("resizeBy", arguments);
        this.resized(_1, _2)
    }
    , isc.A.setWidth = function isc_DrawRect_setWidth(_1) {
        this.resizeTo(_1)
    }
    , isc.A.setHeight = function isc_DrawRect_setHeight(_1) {
        this.resizeTo(null, _1)
    }
    , isc.A.setRect = function isc_DrawRect_setRect(_1, _2, _3, _4) {
        this.moveTo(_1, _2);
        this.resizeTo(_3, _4)
    }
    , isc.A.setRounding = function isc_DrawRect_setRounding(_1) {
        this.rounding = _1;
        if (this.drawingVML) {
            this.erase();
            this.draw()
        } else if (this.drawingSVG) {
            this.$44z.setAttributeNS(null, "rx", (this.rounding ? (this.rounding * Math.min(this.width, this.height) / 2) : null))
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
    }
    );
    isc.B._maxIndex = isc.C + 16;
    isc.defineClass("DrawOval", "DrawItem");
    isc.A = isc.DrawOval.getPrototype();
    isc.B = isc._allFuncs;
    isc.C = isc.B._maxIndex;
    isc.D = isc._funcClasses;
    isc.D[isc.C] = isc.A.Class;
    isc.A.left = 0;
    isc.A.top = 0;
    isc.A.width = 100;
    isc.A.height = 100;
    isc.A.svgElementName = "ellipse";
    isc.A.vmlElementName = "OVAL";
    isc.B.push(isc.A.init = function isc_DrawOval_init() {
        if (!this.radius)
            this.$63t();
        else
            this.$78s();
        this.Super("init")
    }
    , isc.A.$78s = function isc_DrawOval__deriveRectFromRadius() {
        this.rx = this.rx || this.radius;
        this.ry = this.ry || this.radius;
        this.width = this.rx * 2;
        this.height = this.ry * 2;
        if (this.centerPoint != null) {
            this.left = this.centerPoint[0] - this.width / 2;
            this.top = this.centerPoint[1] - this.height / 2
        } else {
            this.centerPoint = [];
            this.centerPoint[0] = this.left + this.width / 2;
            this.centerPoint[1] = this.top + this.height / 2
        }
    }
    , isc.A.$63t = function isc_DrawOval__deriveCenterPointFromRect() {
        this.centerPoint = [];
        this.centerPoint[0] = this.left + this.width / 2;
        this.centerPoint[1] = this.top + this.height / 2;
        this.rx = this.width / 2;
        this.ry = this.height / 2
    }
    , isc.A.getAttributesVML = function isc_DrawOval_getAttributesVML() {
        return isc.SB.concat("STYLE='position:absolute;left:", this.left, "px;top:", this.top, "px;width:", this.width, "px;height:", this.height, "px;'")
    }
    , isc.A.getAttributesSVG = function isc_DrawOval_getAttributesSVG() {
        return"cx='" + this.centerPoint[0] + "' cy='" + this.centerPoint[1] + "' rx='" + this.rx + "' ry='" + this.ry + "'"
    }
    , isc.A.getBoundingBox = function isc_DrawOval_getBoundingBox() {
        return[this.left, this.top, this.left + this.width, this.top + this.height]
    }
    , isc.A.drawBitmapPath = function isc_DrawOval_drawBitmapPath(_1) {
        var _2 = 0.552284749831;
        var _3 = this.rx;
        var _4 = this.ry;
        var _5 = this.centerPoint[0];
        var _6 = this.centerPoint[1];
        _1.moveTo(_5, _6 - _4);
        _1.bezierCurveTo(_5 + (_2 * _3), _6 - _4, _5 + _3, _6 - (_2 * _4), _5 + _3, _6);
        _1.bezierCurveTo(_5 + _3, _6 + (_2 * _4), _5 + (_2 * _3), _6 + _4, _5, _6 + _4);
        _1.bezierCurveTo(_5 - (_2 * _3), _6 + _4, _5 - _3, _6 + (_2 * _4), _5 - _3, _6);
        _1.bezierCurveTo(_5 - _3, _6 - (_2 * _4), _5 - (_2 * _3), _6 - _4, _5, _6 - _4);
        _1.closePath()
    }
    , isc.A.setCenterPoint = function isc_DrawOval_setCenterPoint(_1, _2) {
        if (isc.isAn.Array(_1)) {
            _2 = _1[1];
            _1 = _1[0]
        }
        var _3 = 0, _4 = 0;
        if (_1 != null)
            _3 = _1 - this.centerPoint[0];
        if (_2 != null)
            _4 = _2 - this.centerPoint[1];
        this.moveBy(_3, _4)
    }
    , isc.A.moveBy = function isc_DrawOval_moveBy(_1, _2) {
        if (_1 != null)
            this.centerPoint[0] += _1;
        if (_2 != null)
            this.centerPoint[1] += _2;
        this.$78s();
        if (!this.$if)
            return;
        if (this.drawingVML) {
            this.$44u.style.left = this.centerPoint[0] - this.rx;
            this.$44u.style.top = this.centerPoint[1] - this.ry
        } else if (this.drawingSVG) {
            this.$44z.setAttributeNS(null, "cx", this.centerPoint[0]);
            this.$44z.setAttributeNS(null, "cy", this.centerPoint[1])
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
        this.Super("moveBy", arguments);
        this.moved(_1, _2)
    }
    , isc.A.moveTo = function isc_DrawOval_moveTo(_1, _2) {
        this.moveBy(_1 - this.left, _2 - this.top)
    }
    , isc.A.setLeft = function isc_DrawOval_setLeft(_1) {
        this.moveTo(_1)
    }
    , isc.A.setTop = function isc_DrawOval_setTop(_1) {
        this.moveTo(null, _1)
    }
    , isc.A.resizeBy = function isc_DrawOval_resizeBy(_1, _2, _3) {
        if (_1 != null)
            this.width += _1;
        if (_2 != null)
            this.height += _2;
        if (_3) {
            if (_1 != null)
                this.left += Math.round(_1 / 2);
            if (_2 != null)
                this.top += Math.round(_2 / 2)
        }
        this.$63t();
        if (this.drawingVML) {
            this.$44u.style.width = this.width;
            this.$44u.style.height = this.height;
            if (_3) {
                this.$44u.style.left = this.left;
                this.$44u.style.top = this.top
            }
        } else if (this.drawingSVG) {
            this.$44z.setAttributeNS(null, "rx", this.rx);
            this.$44z.setAttributeNS(null, "ry", this.ry);
            if (!_3) {
                this.$44z.setAttributeNS(null, "cx", this.centerPoint[0]);
                this.$44z.setAttributeNS(null, "cy", this.centerPoint[1])
            }
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
        this.Super("resizeBy", arguments);
        this.resized(_1, _2);
        if (_3)
            this.moved(_1 / 2, _2 / 2)
    }
    , isc.A.resizeTo = function isc_DrawOval_resizeTo(_1, _2, _3) {
        var _4 = 0, _5 = 0;
        if (_1 != null)
            _4 = _1 - this.width;
        if (_2 != null)
            _5 = _2 - this.height;
        this.resizeBy(_4, _5, _3)
    }
    , isc.A.setWidth = function isc_DrawOval_setWidth(_1) {
        this.resizeTo(_1)
    }
    , isc.A.setHeight = function isc_DrawOval_setHeight(_1) {
        this.resizeTo(null, _1)
    }
    , isc.A.setRect = function isc_DrawOval_setRect(_1, _2, _3, _4) {
        this.moveTo(_1, _2);
        this.resizeTo(_3, _4)
    }
    , isc.A.setRadii = function isc_DrawOval_setRadii(_1, _2) {
        if (isc.isAn.Array(_1)) {
            _2 = _1[1];
            _1 = _1[0]
        }
        var _3, _4;
        if (_1 != null)
            _3 = 2 * _1;
        if (_2 != null)
            _4 = 2 * _2;
        this.resizeTo(_3, _4, true)
    }
    , isc.A.setRadius = function isc_DrawOval_setRadius(_1) {
        this.setRadii(_1, _1)
    }
    , isc.A.setOval = function isc_DrawOval_setOval(_1, _2, _3, _4) {
        this.setCenterPoint(_1, _2);
        this.setRadii(_3, _4)
    }
    , isc.A.getCenter = function isc_DrawOval_getCenter() {
        return this.centerPoint.slice()
    }
    );
    isc.B._maxIndex = isc.C + 21;
    isc.defineClass("DrawSector", "DrawItem");
    isc.A = isc.DrawSector.getPrototype();
    isc.B = isc._allFuncs;
    isc.C = isc.B._maxIndex;
    isc.D = isc._funcClasses;
    isc.D[isc.C] = isc.A.Class;
    isc.A.centerPoint = [0, 0];
    isc.A.startAngle = 0.0;
    isc.A.endAngle = 20.0;
    isc.A.radius = 100;
    isc.A.svgElementName = "path";
    isc.A.vmlElementName = "SHAPE";
    isc.B.push(isc.A.init = function isc_DrawSector_init() {
        this.Super("init")
    }
    , isc.A.getBoundingBox = function isc_DrawSector_getBoundingBox() {
        return[this.centerPoint[0], this.centerPoint[1], this.centerPoint[0] + this.radius, this.centerPoint[1] + this.radius]
    }
    , isc.A.getCenter = function isc_DrawSector_getCenter() {
        return this.centerPoint.slice()
    }
    , isc.A.getAttributesVML = function isc_DrawSector_getAttributesVML() {
        var _1 = -this.startAngle;
        var _2 = this.endAngle - this.startAngle;
        var _3 = "STYLE='position:absolute;top:0px;left:0px;width:100%;height:100%;' ";
        var _4 = "path='m" + Math.round(this.centerPoint[0]) + "," + Math.round(this.centerPoint[1]);
        _4 += " ae" + Math.round(this.centerPoint[0]) + "," + Math.round(this.centerPoint[1]);
        _4 += " " + Math.round(this.radius) + "," + Math.round(this.radius) + " ";
        _4 += Math.round(_1 * 65535) + "," + Math.round(-_2 * 65536) + " x e'";
        return _3 + _4
    }
    , isc.A.getAttributesSVG = function isc_DrawSector_getAttributesSVG() {
        var _1 = this.endAngle - this.startAngle + 1;
        var _2 = _1 * Math.PI / 180.0;
        var _3 = this.startAngle * Math.PI / 180.0;
        var _4 = this.radius;
        var _5 = 0.0;
        var _6 = this.centerPoint[0] + _4 * Math.cos(_3);
        var _7 = this.centerPoint[1] + _4 * Math.sin(_3);
        var _8 = _4 * Math.cos(_2);
        var _9 = _4 * Math.sin(_2);
        var _10 = this.centerPoint[0] + _8 * Math.cos(_3) - _9 * Math.sin(_3);
        var _11 = this.centerPoint[1] + _8 * Math.sin(_3) + _9 * Math.cos(_3);
        var _12 = "d='M" + this.centerPoint[0] + " " + this.centerPoint[1];
        _12 += " L" + _6 + " " + _7;
        _12 += " A" + this.radius + " " + this.radius + " 0 0 1 ";
        _12 += _10 + " " + _11 + " Z";
        return _12
    }
    , isc.A.drawBitmapPath = function isc_DrawSector_drawBitmapPath(_1) {
        var _2 = this.endAngle - this.startAngle + 1;
        var _3 = _2 * Math.PI / 180.0;
        var _4 = this.startAngle * Math.PI / 180.0;
        var _5 = this.endAngle * Math.PI / 180.0;
        var _6 = this.startAngle * Math.PI / 180.0;
        var _7 = this.radius;
        var _8 = 0.0;
        var _9 = this.centerPoint[0] + _7 * Math.cos(_6);
        var _10 = this.centerPoint[1] + _7 * Math.sin(_6);
        var _11 = _7 * Math.cos(_3);
        var _12 = _7 * Math.sin(_3);
        var _13 = this.centerPoint[0] + _11 * Math.cos(_6) - _12 * Math.sin(_6);
        var _14 = this.centerPoint[1] + _11 * Math.sin(_6) + _12 * Math.cos(_6);
        this.bmMoveTo(this.centerPoint[0], this.centerPoint[1], _1);
        this.bmLineTo(_9, _10, _1);
        this.bmArc(this.centerPoint[0], this.centerPoint[1], this.radius, _4, _5, false);
        this.bmLineTo(this.centerPoint[0], this.centerPoint[1], _1);
        _1.closePath()
    }
    , isc.A.setCenterPoint = function isc_DrawSector_setCenterPoint(_1, _2) {
        if (isc.isAn.Array(_1)) {
            _2 = _1[1];
            _1 = _1[0]
        }
        var _3 = 0, _4 = 0;
        if (_1 != null)
            _3 = _1 - this.centerPoint[0];
        if (_2 != null)
            _4 = _2 - this.centerPoint[1];
        this.moveBy(_3, _4)
    }
    , isc.A.moveTo = function isc_DrawSector_moveTo(_1, _2) {
        this.moveBy(_1 - this.centerPoint[0], _2 - this.centerPoint[1])
    }
    , isc.A.moveBy = function isc_DrawSector_moveBy(_1, _2) {
        if (!this.$if)
            return;
        this.centerPoint[0] += _1;
        this.centerPoint[1] += _2;
        if (this.drawingVML) {
            this.$44u.path = "m" + Math.round(this.centerPoint[0]) + "," + Math.round(this.centerPoint[1]) + " ae" + Math.round(this.centerPoint[0]) + "," + Math.round(this.centerPoint[1]) + " " + Math.round(this.radius) + "," + Math.round(this.radius) + " " + Math.round(-this.startAngle * 65535) + "," + Math.round(-(this.endAngle - this.startAngle) * 65536) + " x e'"
        } else if (this.drawingSVG) {
            this.$44z.setAttributeNS(null, "transform", "translate(" + _1 + "," + _2 + ")")
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
        this.Super("moveBy", arguments);
        this.moved()
    }
    , isc.A.resizeBy = function isc_DrawSector_resizeBy(_1, _2) {
        if (this.scale) {
            this.scale[0] = _1;
            this.scale[1] = _2;
            if (this.drawingSVG) {
                this.$44z.setAttributeNS(null, "transform", "scale(" + this.scale[0] + "," + this.scale[1] + ")")
            } else if (this.drawingBitmap) {
                this.drawPane.redrawBitmap()
            }
            this.Super("resizeBy", arguments);
            this.resized(_1, _2)
        }
    }
    );
    isc.B._maxIndex = isc.C + 10;
    isc.defineClass("DrawLabel", "DrawItem");
    isc.A = isc.DrawLabel.getPrototype();
    isc.B = isc._allFuncs;
    isc.C = isc.B._maxIndex;
    isc.D = isc._funcClasses;
    isc.D[isc.C] = isc.A.Class;
    isc.A.left = 0;
    isc.A.top = 0;
    isc.A.fontFamily = "Tahoma";
    isc.A.fontSize = 18;
    isc.A.fontWeight = "bold";
    isc.A.fontStyle = "normal";
    isc.B.push(isc.A.init = function isc_DrawLabel_init() {
        this.$44k = this.fontSize;
        this.Super("init")
    }
    , isc.A.$44v = function isc_DrawLabel__getElementVML(_1) {
        if (this.synchTextMove) {
            return isc.SB.concat(this.drawPane.startTagVML('RECT'), " ID='", _1, "' STYLE='position:absolute;left:", this.left, "px; top:", this.top, "px;'>", this.drawPane.startTagVML('TEXTBOX'), " INSET='0px, 0px, 0px, 0px' STYLE='overflow:visible", "; font-family:", this.fontFamily, "; font-size:", this.fontSize * this.drawPane.zoomLevel, "; font-weight:", this.fontWeight, "; font-style:", this.fontStyle, ";'><NOBR>", this.contents, "</NOBR>", this.drawPane.endTagVML('TEXTBOX'), this.drawPane.endTagVML('RECT'))
        } else {
            var _2 = this.drawPane.drawing2screen([this.left, this.top, 0, 0]);
            return isc.SB.concat("<DIV ID='", _1, "' STYLE='position:absolute; overflow:visible; width:1px; height:1px", "; left:", _2[0] + this.drawPane.getLeftPadding(), "px; top:", _2[1] + this.drawPane.getTopPadding(), "px", (this.rotation ? ";writing-mode: tb-rl" : ""), "; font-family:", this.fontFamily, "; font-size:", (this.fontSize * this.drawPane.zoomLevel) + "px", "; font-weight:", this.fontWeight, "; font-style:", this.fontStyle, ";color:", this.lineColor, ";'><NOBR>", this.contents, "</NOBR></DIV>")
        }
    }
    , isc.A.$440 = function isc_DrawLabel__getElementSVG(_1) {
        var _2;
        if (!isc.Browser.isWebKit) {
            _2 = "<text id='" + _1 + "' x='" + this.left + "' y='" + this.top + "' dominant-baseline='text-before-edge" + "' font-family='" + this.fontFamily + "' font-size='" + this.fontSize + "' font-weight='" + this.fontWeight + "' font-style='" + this.fontStyle + "'>" + this.contents + "</text>"
        } else {
            _2 = this.$44m.createElementNS("http://www.w3.org/2000/svg", "text");
            _2.setAttributeNS(null, "id", _1);
            _2.setAttributeNS(null, "x", this.left);
            _2.setAttributeNS(null, "y", this.top);
            _2.setAttributeNS(null, "dominantBaseline", "text-before-edge");
            _2.setAttributeNS(null, "fontFamily", this.fontFamily);
            _2.setAttributeNS(null, "fontSize", this.fontSize);
            _2.setAttributeNS(null, "fontWeight", this.fontWeight);
            _2.setAttributeNS(null, "fontStyle", this.fontStyle);
            _2.textContent = this.contents
        }
        return _2
    }
    , isc.A.moveBy = function isc_DrawLabel_moveBy(_1, _2) {
        this.top += _2;
        this.left += _1;
        if (this.drawingVML) {
            this.$44u.style.left = this.left;
            this.$44u.style.top = this.top
        } else if (this.drawingSVG) {
            this.$44z.setAttributeNS(null, "x", this.left);
            this.$44z.setAttributeNS(null, "y", this.top)
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
        this.Super("moveBy", arguments)
    }
    , isc.A.moveTo = function isc_DrawLabel_moveTo(_1, _2) {
        this.moveBy(_1 - this.left, _2 - this.top)
    }
    , isc.A.rotateTo = function isc_DrawLabel_rotateTo(_1) {
        this.rotateBy(_1 - this.rotation)
    }
    , isc.A.getCenter = function isc_DrawLabel_getCenter() {
        return[this.left + Math.round(this.getTextWidth() / 2), this.top + Math.round(this.getTextHeight() / 2)]
    }
    , isc.A.getBoundingBox = function isc_DrawLabel_getBoundingBox() {
        return[this.left, this.top, this.left + this.getTextWidth(), this.top + this.getTextHeight()]
    }
    , isc.A.makeHTMLText = function isc_DrawLabel_makeHTMLText() {
        var _1 = this.$45c = isc.HTMLFlow.create({left: this.left, top: this.top, rotation: this.rotation, transformOrigin: "0 0", width: 1, height: 1, contents: isc.SB.concat("<span style='font-family:", this.fontFamily, ";font-weight:", this.fontWeight, ";font-size:", Math.round(this.fontSize * this.drawPane.zoomLevel), ";font-style:", this.fontStyle, ";color:", this.lineColor, ";white-space:nowrap'>", this.contents, "</span>"), autoDraw: false});
        this.drawPane.addChild(_1)
    }
    , isc.A.drawBitmap = function isc_DrawLabel_drawBitmap(_1) {
        if (this.useHTML || isc.Browser.isIPhone) {
            if (!this.$45c)
                this.makeHTMLText()
        } else {
            this.Super("drawBitmap", arguments)
        }
    }
    , isc.A.erase = function isc_DrawLabel_erase() {
        if (this.$45c)
            this.$45c.destroy();
        this.Super("erase", arguments)
    }
    , isc.A.destroy = function isc_DrawLabel_destroy() {
        if (this.$45c)
            this.$45c.destroy();
        this.Super("destroy", arguments)
    }
    , isc.A.getFontString = function isc_DrawLabel_getFontString() {
        return(this.fontStyle != "normal" ? this.fontStyle + " " : "") + (this.fontWeight != "normal" ? this.fontWeight + " " : "") + this.fontSize + "px " + this.fontFamily
    }
    , isc.A.drawBitmapPath = function isc_DrawLabel_drawBitmapPath(_1) {
        _1.textBaseline = "top";
        _1.font = this.getFontString();
        this.bmTranslate(this.left, this.top, _1);
        if (this.rotation) {
            this.bmRotate((this.rotation / 180) * Math.PI, _1)
        }
        if (this.backgroundColor) {
            _1.fillStyle = this.backgroundColor;
            _1.globalAlpha = this.fillOpacity;
            _1.beginPath();
            var _2 = 0, _3 = this.getTextWidth(), _4 = 0, _5 = this.getTextHeight();
            this.bmMoveTo(_2, _4);
            this.bmLineTo(_3, _4);
            this.bmLineTo(_3, _5);
            this.bmLineTo(_2, _5);
            this.bmLineTo(_2, _4);
            _1.fill()
        }
        _1.fillStyle = this.lineColor;
        this.bmFillText(this.contents, 0, 0, _1)
    }
    , isc.A.getTextWidth = function isc_DrawLabel_getTextWidth() {
        if (this.drawingVML) {
            if (this.synchTextMove) {
                return this.$44u.firstChild.scrollWidth
            } else {
                return this.$44u.scrollWidth
            }
        } else if (this.drawingSVG) {
            return this.$44z.getComputedTextLength()
        } else if (this.drawingBitmap) {
            var _1 = this.drawPane.getBitmapContext();
            _1.font = _1.font || this.getFontString();
            return _1.measureText(this.contents).width
        }
    }
    , isc.A.getTextHeight = function isc_DrawLabel_getTextHeight() {
        if (this.drawingVML) {
            if (this.synchTextMove) {
                return this.$44u.firstChild.scrollHeight
            } else {
                return this.$44u.scrollWidth
            }
        } else if (this.drawingSVG) {
            return this.$44z.getBBox().height
        } else if (this.drawingBitmap) {
            var _1 = this.drawPane.getBitmapContext();
            _1.font = _1.font || this.getFontString();
            return _1.measureText(this.contents).height || this.fontSize
        }
    }
    , isc.A.setFontSize = function isc_DrawLabel_setFontSize(_1) {
        if (_1 != null)
            this.fontSize = _1;
        if (this.drawingVML) {
            this.$44j.fontSize = this.fontSize * this.drawPane.zoomLevel
        } else if (this.drawingSVG) {
            this.$44z.setAttributeNS(null, "font-size", this.fontSize)
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
    }
    , isc.A.setLineWidth = function isc_DrawLabel_setLineWidth(_1) {
        this.logWarn("no setLineWidth")
    }
    , isc.A.$44l = function isc_DrawLabel__setLineWidthVML(_1) {
        this.logWarn("no $44l")
    }
    , isc.A.setLineColor = function isc_DrawLabel_setLineColor(_1) {
        this.logWarn("no setLineColor")
    }
    , isc.A.setLineOpacity = function isc_DrawLabel_setLineOpacity(_1) {
        this.logWarn("no setLineOpacity")
    }
    , isc.A.setLinePattern = function isc_DrawLabel_setLinePattern(_1) {
        this.logWarn("no setLinePattern")
    }
    , isc.A.setLineCap = function isc_DrawLabel_setLineCap(_1) {
        this.logWarn("no setLineCap")
    }
    , isc.A.setStartArrow = function isc_DrawLabel_setStartArrow(_1) {
        this.logWarn("no setStartArrow")
    }
    , isc.A.setEndArrow = function isc_DrawLabel_setEndArrow(_1) {
        this.logWarn("no setEndArrow")
    }
    , isc.A.setFillColor = function isc_DrawLabel_setFillColor(_1) {
        this.logWarn("no setFillColor")
    }
    , isc.A.setFillOpacity = function isc_DrawLabel_setFillOpacity(_1) {
        this.logWarn("no setFillOpacity")
    }
    );
    isc.B._maxIndex = isc.C + 27;
    isc.defineClass("DrawImage", "DrawItem");
    isc.A = isc.DrawImage.getPrototype();
    isc.B = isc._allFuncs;
    isc.C = isc.B._maxIndex;
    isc.D = isc._funcClasses;
    isc.D[isc.C] = isc.A.Class;
    isc.A.left = 0;
    isc.A.top = 0;
    isc.A.width = 16;
    isc.A.height = 16;
    isc.A.src = "blank.png";
    isc.A.image = null;
    isc.B.push(isc.A.getBoundingBox = function isc_DrawImage_getBoundingBox() {
        return[this.left, this.top, this.left + this.width, this.top + this.height]
    }
    , isc.A.init = function isc_DrawImage_init() {
        this.Super("init");
        if (this.src) {
            this.src = this.getSrcURL(this.src);
            this.image = new Image();
            this.image.src = this.src
        }
    }
    , isc.A.getSrcURL = function isc_DrawImage_getSrcURL(_1) {
        var _2 = isc.Canvas.getImgURL(_1);
        return _2
    }
    , isc.A.$44v = function isc_DrawImage__getElementVML(_1) {
        var _2 = isc.SB.concat(this.drawPane.startTagVML('IMAGE'), " ID='", _1, "' SRC='", this.getSrcURL(this.src), (this.title ? "' ALT='" + this.title : ""), "' STYLE='left:", this.left, "px; top:", this.top, "px; width:", this.width, "px; height:", this.height, "px;'>", this.drawPane.endTagVML("IMAGE"));
        return _2
    }
    , isc.A.$440 = function isc_DrawImage__getElementSVG(_1) {
        var _2, _3 = this.getCenter();
        if (!isc.Browser.isWebKit) {
            _2 = "<image id='" + _1 + ((this.rotation && _3 && _3.length === 2) ? "' transform='translate(" + _3[0] + "," + _3[1] + ") rotate(" + this.rotation + ") translate(" + -_3[0] + "," + -_3[1] + ")" : "") + "' x='" + this.left + "' y='" + this.top + "' width='" + this.width + "px' height='" + this.height + "px' xlink:href='" + this.getSrcURL(this.src) + "'>" + (this.title ? "<title>" + this.title + "</title>" : "") + "</image>"
        } else {
            _2 = this.$44m.createElementNS("http://www.w3.org/2000/svg", "image");
            _2.setAttributeNS(null, "id", _1);
            _2.setAttributeNS(null, "x", this.left);
            _2.setAttributeNS(null, "y", this.top);
            _2.setAttributeNS(null, "width", this.width + "px");
            _2.setAttributeNS(null, "height", this.height + "px");
            _2.setAttributeNS("http://www.w3.org/1999/xlink", "href", this.getSrcURL(this.src));
            if (this.rotation) {
                _2.setAttributeNS(null, "transform", "translate(" + _3[0] + "," + _3[1] + ") rotate(" + this.rotation + ") translate(" + -_3[0] + "," + -_3[1] + ")")
            }
            if (this.title) {
                var _4 = this.$44m.createElementNS("http://www.w3.org/2000/svg", "title");
                _4.textContent = this.title;
                _2.appendChild(_4)
            }
        }
        return _2
    }
    , isc.A.drawBitmapPath = function isc_DrawImage_drawBitmapPath(_1) {
        _1.drawImage(this.image, this.left, this.top, this.width, this.height)
    }
    , isc.A.getCenter = function isc_DrawImage_getCenter() {
        return[this.left + Math.round(this.width / 2), this.top + Math.round(this.height / 2)]
    }
    , isc.A.setSrc = function isc_DrawImage_setSrc(_1) {
        if (_1 != null) {
            this.src = this.getSrcURL(_1);
            this.image = new Image();
            this.image.src = this.src
        }
        if (this.drawingVML) {
            this.$44u.src = this.src
        } else if (this.drawingSVG) {
            this.$44z.setAttributeNS("http://www.w3.org/1999/xlink", "href", this.src)
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
    }
    , isc.A.setLineWidth = function isc_DrawImage_setLineWidth(_1) {
        this.logWarn("no setLineWidth")
    }
    , isc.A.$44l = function isc_DrawImage__setLineWidthVML(_1) {
        this.logWarn("no $44l")
    }
    , isc.A.setLineColor = function isc_DrawImage_setLineColor(_1) {
        this.logWarn("no setLineColor")
    }
    , isc.A.setLineOpacity = function isc_DrawImage_setLineOpacity(_1) {
        this.logWarn("no setLineOpacity")
    }
    , isc.A.setLinePattern = function isc_DrawImage_setLinePattern(_1) {
        this.logWarn("no setLinePattern")
    }
    , isc.A.setLineCap = function isc_DrawImage_setLineCap(_1) {
        this.logWarn("no setLineCap")
    }
    , isc.A.setStartArrow = function isc_DrawImage_setStartArrow(_1) {
        this.logWarn("no setStartArrow")
    }
    , isc.A.setEndArrow = function isc_DrawImage_setEndArrow(_1) {
        this.logWarn("no setEndArrow")
    }
    , isc.A.setFillColor = function isc_DrawImage_setFillColor(_1) {
        this.logWarn("no setFillColor")
    }
    , isc.A.setFillOpacity = function isc_DrawImage_setFillOpacity(_1) {
        this.logWarn("no setFillOpacity")
    }
    , isc.A.moveBy = function isc_DrawImage_moveBy(_1, _2) {
        this.left += _1;
        this.top += _2;
        if (this.drawingVML) {
            this.$44u.style.left = this.left;
            this.$44u.style.top = this.top
        } else if (this.drawingSVG) {
            this.$44z.setAttributeNS(null, "x", this.left);
            this.$44z.setAttributeNS(null, "y", this.top)
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
        this.Super("moveBy", arguments);
        this.moved(_1, _2)
    }
    , isc.A.moveTo = function isc_DrawImage_moveTo(_1, _2) {
        this.moveBy(_1 - this.left, _2 - this.top)
    }
    );
    isc.B._maxIndex = isc.C + 20;
    isc.defineClass("DrawCurve", "DrawItem");
    isc.A = isc.DrawCurve.getPrototype();
    isc.B = isc._allFuncs;
    isc.C = isc.B._maxIndex;
    isc.D = isc._funcClasses;
    isc.D[isc.C] = isc.A.Class;
    isc.A.startPoint = [0, 0];
    isc.A.endPoint = [100, 100];
    isc.A.controlPoint1 = [100, 0];
    isc.A.controlPoint2 = [0, 100];
    isc.A.svgElementName = "path";
    isc.A.vmlElementName = "curve";
    isc.A.lineCap = "butt";
    isc.B.push(isc.A.init = function isc_DrawCurve_init() {
        this.Super("init")
    }
    , isc.A.$636 = function isc_DrawCurve__getKnobPosition(_1) {
        var x, y;
        x = this.endPoint[0] - this.startPoint[0];
        y = this.endPoint[1] - this.startPoint[1];
        return[x, y]
    }
    , isc.A.getCenter = function isc_DrawCurve_getCenter() {
        return[this.startPoint[0] + Math.round((this.endPoint[0] - this.startPoint[0]) / 2), this.startPoint[1] + Math.round((this.endPoint[1] - this.startPoint[1]) / 2)]
    }
    , isc.A.getAttributesVML = function isc_DrawCurve_getAttributesVML() {
        return isc.SB.concat("FROM='", this.startPoint[0], " ", this.startPoint[1], "' TO='", this.endPoint[0], " ", this.endPoint[1], "' CONTROL1='", this.controlPoint1[0], " ", this.controlPoint1[1], "' CONTROL2='", this.controlPoint2[0], " ", this.controlPoint2[1], "'")
    }
    , isc.A.getAttributesSVG = function isc_DrawCurve_getAttributesSVG() {
        return"d='" + this.getPathSVG() + "'"
    }
    , isc.A.getPathSVG = function isc_DrawCurve_getPathSVG() {
        return"M" + this.startPoint[0] + " " + this.startPoint[1] + "C" + this.controlPoint1[0] + " " + this.controlPoint1[1] + " " + this.controlPoint2[0] + " " + this.controlPoint2[1] + " " + this.endPoint[0] + " " + this.endPoint[1]
    }
    , isc.A.drawBitmapPath = function isc_DrawCurve_drawBitmapPath(_1) {
        _1.moveTo(this.startPoint[0], this.startPoint[1]);
        _1.bezierCurveTo(this.controlPoint1[0], this.controlPoint1[1], this.controlPoint2[0], this.controlPoint2[1], this.endPoint[0], this.endPoint[1])
    }
    , isc.A.setStartPoint = function isc_DrawCurve_setStartPoint(_1, _2) {
        if (_1 != null)
            this.startPoint[0] = _1;
        if (_2 != null)
            this.startPoint[1] = _2;
        if (this.drawingVML) {
            this.$44u.from = this.startPoint[0] + " " + this.startPoint[1]
        } else if (this.drawingSVG) {
            this.$44z.setAttributeNS(null, "d", this.getPathSVG())
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
        this.updateControlKnobs()
    }
    , isc.A.setEndPoint = function isc_DrawCurve_setEndPoint(_1, _2) {
        if (_1 != null)
            this.endPoint[0] = _1;
        if (_2 != null)
            this.endPoint[1] = _2;
        if (this.drawingVML) {
            this.$44u.to = this.endPoint[0] + " " + this.endPoint[1]
        } else if (this.drawingSVG) {
            this.$44z.setAttributeNS(null, "d", this.getPathSVG())
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
        this.updateControlKnobs()
    }
    , isc.A.setControlPoint1 = function isc_DrawCurve_setControlPoint1(_1, _2) {
        if (_1 != null)
            this.controlPoint1[0] = _1;
        if (_2 != null)
            this.controlPoint1[1] = _2;
        if (this.drawingVML) {
            this.$44u.control1 = this.controlPoint1[0] + " " + this.controlPoint1[1]
        } else if (this.drawingSVG) {
            this.$44z.setAttributeNS(null, "d", this.getPathSVG())
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
        this.updateControlKnobs()
    }
    , isc.A.setControlPoint2 = function isc_DrawCurve_setControlPoint2(_1, _2) {
        if (_1 != null)
            this.controlPoint2[0] = _1;
        if (_2 != null)
            this.controlPoint2[1] = _2;
        if (this.drawingVML) {
            this.$44u.control2 = this.controlPoint2[0] + " " + this.controlPoint2[1]
        } else if (this.drawingSVG) {
            this.$44z.setAttributeNS(null, "d", this.getPathSVG())
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
        this.updateControlKnobs()
    }
    , isc.A.getBoundingBox = function isc_DrawCurve_getBoundingBox() {
        return[].concat(this.startPoint, this.endPoint)
    }
    , isc.A.showStartPointKnobs = function isc_DrawCurve_showStartPointKnobs() {
        this.$639 = this.createAutoChild("startKnob", {_constructor: "DrawKnob", x: this.startPoint[0], y: this.startPoint[1], drawPane: this.drawPane, autoDraw: true});
        this.observe(this.$639, "updatePoints", "observer.setStartPoint(x,y)")
    }
    , isc.A.hideStartPointKnobs = function isc_DrawCurve_hideStartPointKnobs() {
        if (this.$639) {
            this.$639.destroy();
            delete this.$639
        }
    }
    , isc.A.showEndPointKnobs = function isc_DrawCurve_showEndPointKnobs() {
        this.$64a = this.createAutoChild("endKnob", {_constructor: "DrawKnob", x: this.endPoint[0], y: this.endPoint[1], drawPane: this.drawPane, autoDraw: true});
        this.observe(this.$64a, "updatePoints", "observer.setEndPoint(x,y)")
    }
    , isc.A.hideEndPointKnobs = function isc_DrawCurve_hideEndPointKnobs() {
        if (this.$64a) {
            this.$64a.destroy();
            delete this.$64a
        }
    }
    , isc.A.showControlPoint1Knobs = function isc_DrawCurve_showControlPoint1Knobs() {
        this.$64b = this.createAutoChild("c1Knob", {_constructor: "DrawKnob", x: this.controlPoint1[0], y: this.controlPoint1[1], drawPane: this.drawPane, autoDraw: true});
        this.$64c = this.createAutoChild("c1Line", {_constructor: "DrawLine", startLeft: this.startPoint[0], startTop: this.startPoint[1], endLeft: this.controlPoint1[0], endTop: this.controlPoint1[1], drawPane: this.drawPane, autoDraw: true});
        this.observe(this.$64b, "updatePoints", "observer.setControlPoint1(x,y)")
    }
    , isc.A.hideControlPoint1Knobs = function isc_DrawCurve_hideControlPoint1Knobs() {
        if (this.$64b) {
            this.$64b.destroy();
            delete this.$64b
        }
        if (this.$64c) {
            this.$64c.erase();
            delete this.$64c
        }
    }
    , isc.A.showControlPoint2Knobs = function isc_DrawCurve_showControlPoint2Knobs() {
        this.$64d = this.createAutoChild("c2Knob", {_constructor: "DrawKnob", x: this.controlPoint2[0], y: this.controlPoint2[1], drawPane: this.drawPane, autoDraw: true});
        this.$64e = this.createAutoChild("c2Line", {_constructor: "DrawLine", startLeft: this.endPoint[0], startTop: this.endPoint[1], endLeft: this.controlPoint2[0], endTop: this.controlPoint2[1], drawPane: this.drawPane, autoDraw: true});
        this.observe(this.$64d, "updatePoints", "observer.setControlPoint2(x,y)")
    }
    , isc.A.hideControlPoint2Knobs = function isc_DrawCurve_hideControlPoint2Knobs() {
        if (this.$64d) {
            this.$64d.destroy();
            delete this.$64d
        }
        if (this.$64e) {
            this.$64e.erase();
            delete this.$64e
        }
    }
    , isc.A.updateControlKnobs = function isc_DrawCurve_updateControlKnobs() {
        this.Super("updateControlKnobs", arguments);
        if (this.$639 || this.$64c) {
            var _1 = this.startPoint[0], _2 = this.startPoint[1];
            if (this.$64c)
                this.$64c.setStartPoint(_1, _2);
            if (this.$639) {
                var _3 = this.drawPane.drawing2screen([_1, _2, 0, 0]);
                this.$639.setCenterPoint(_3[0], _3[1])
            }
        }
        if (this.$64a || this.$64e) {
            var _1 = this.endPoint[0], _2 = this.endPoint[1];
            if (this.$64e)
                this.$64e.setStartPoint(_1, _2);
            if (this.$64a) {
                var _3 = this.drawPane.drawing2screen([_1, _2, 0, 0]);
                this.$64a.setCenterPoint(_3[0], _3[1])
            }
        }
        if (this.$64b) {
            var _1 = this.controlPoint1[0], _2 = this.controlPoint1[1];
            this.$64c.setEndPoint(_1, _2);
            var _3 = this.drawPane.drawing2screen([_1, _2, 0, 0]);
            this.$64b.setCenterPoint(_3[0], _3[1])
        }
        if (this.$64d) {
            var _1 = this.controlPoint2[0], _2 = this.controlPoint2[1];
            this.$64e.setEndPoint(_1, _2);
            var _3 = this.drawPane.drawing2screen([_1, _2, 0, 0]);
            this.$64d.setCenterPoint(_3[0], _3[1])
        }
    }
    , isc.A.moveTo = function isc_DrawCurve_moveTo(_1, _2) {
        this.moveBy(_1 - this.startPoint[0], _2 - this.startPoint[1])
    }
    , isc.A.moveBy = function isc_DrawCurve_moveBy(_1, _2) {
        this.startPoint[0] += _1;
        this.startPoint[1] += _2;
        this.controlPoint1[0] += _1;
        this.controlPoint1[1] += _2;
        this.controlPoint2[0] += _1;
        this.controlPoint2[1] += _2;
        this.endPoint[0] += _1;
        this.endPoint[1] += _2;
        if (this.drawingVML) {
            this.$44u.from = this.startPoint[0] + " " + this.startPoint[1];
            this.$44u.to = this.endPoint[0] + " " + this.endPoint[1];
            this.$44u.control1 = this.controlPoint1[0] + " " + this.controlPoint1[1];
            this.$44u.control2 = this.controlPoint2[0] + " " + this.controlPoint2[1]
        } else if (this.drawingSVG) {
            this.$44z.setAttributeNS(null, "d", this.getPathSVG())
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
        this.Super("moveBy", arguments);
        this.moved(_1, _2)
    }
    );
    isc.B._maxIndex = isc.C + 23;
    isc.defineClass("DrawBlockConnector", "DrawCurve");
    isc.A = isc.DrawBlockConnector.getPrototype();
    isc.B = isc._allFuncs;
    isc.C = isc.B._maxIndex;
    isc.D = isc._funcClasses;
    isc.D[isc.C] = isc.A.Class;
    isc.A.startPoint = [0, 0];
    isc.A.endPoint = [100, 100];
    isc.A.controlPoint1 = [100, 0];
    isc.A.controlPoint2 = [0, 100];
    isc.A.svgElementName = "path";
    isc.A.vmlElementName = "curve";
    isc.B.push(isc.A.init = function isc_DrawBlockConnector_init() {
        this.Super("init")
    }
    , isc.A.getAttributesVML = function isc_DrawBlockConnector_getAttributesVML() {
        return"FROM='" + this.startPoint[0] + " " + this.startPoint[1] + "' TO='" + this.endPoint[0] + " " + this.endPoint[1] + "' CONTROL1='" + this.controlPoint1[0] + " " + this.controlPoint1[1] + "' CONTROL2='" + this.controlPoint2[0] + " " + this.controlPoint2[1] + "'"
    }
    , isc.A.getAttributesSVG = function isc_DrawBlockConnector_getAttributesSVG() {
        return"d='" + this.getPathSVG() + "'"
    }
    , isc.A.setStartPoint = function isc_DrawBlockConnector_setStartPoint(_1, _2) {
        if (_1 != null)
            this.startPoint[0] = _1;
        if (_2 != null)
            this.startPoint[1] = _2;
        if (this.drawingVML) {
            this.$44u.from = this.startPoint[0] + " " + this.startPoint[1]
        } else if (this.drawingSVG) {
            this.$44z.setAttributeNS(null, "d", this.getPathSVG())
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
    }
    );
    isc.B._maxIndex = isc.C + 4;
    isc.defineClass("DrawPath", "DrawItem");
    isc.A = isc.DrawPath.getPrototype();
    isc.B = isc._allFuncs;
    isc.C = isc.B._maxIndex;
    isc.D = isc._funcClasses;
    isc.D[isc.C] = isc.A.Class;
    isc.A.points = [[0, 0], [100, 100]];
    isc.A.svgElementName = "polyline";
    isc.A.vmlElementName = "POLYLINE";
    isc.B.push(isc.A.getBoundingBox = function isc_DrawPath_getBoundingBox() {
        var _1 = this.points[0], _2 = [_1[0], _1[1]], _3 = [_1[0], _1[1]], i;
        for (i = 1; i < this.points.length; ++i) {
            _1 = this.points[i];
            _2[0] = Math.min(_2[0], _1[0]);
            _2[1] = Math.min(_2[1], _1[1]);
            _3[0] = Math.max(_3[0], _1[0]);
            _3[1] = Math.max(_3[1], _1[1])
        }
        return[].concat(_2, _3)
    }
    , isc.A.getPointsText = function isc_DrawPath_getPointsText() {
        var _1 = isc.SB.create(), _2 = this.points;
        for (var i = 0; i < _2.length; i++) {
            _1.append(_2[i][0], " ", _2[i][1]);
            if (i < _2.length - 1)
                _1.append(" ")
        }
        return _1.toString()
    }
    , isc.A.getAttributesVML = function isc_DrawPath_getAttributesVML() {
        return isc.SB.concat("STYLE='position:absolute; left:", this.left, "px; top:", this.top, "px; width:", this.width, "px; height:", this.height, "px;'", " POINTS='" + this.getPointsText() + "'")
    }
    , isc.A.getAttributesSVG = function isc_DrawPath_getAttributesSVG() {
        return"points='" + this.getPointsText() + "'"
    }
    , isc.A.drawBitmapPath = function isc_DrawPath_drawBitmapPath(_1) {
        var _2 = this.points;
        this.bmMoveTo(_2[0][0], _2[0][1], _1);
        for (var i = 1; i < this.points.length; i++) {
            var _4 = _2[i];
            if (this.linePattern.toLowerCase() !== "solid") {
                this.$85x(_2[i - 1][0], _2[i - 1][1], _2[i][0], _2[i][1], _1)
            } else {
                this.bmLineTo(_4[0], _4[1], _1)
            }
        }
    }
    , isc.A.getCenter = function isc_DrawPath_getCenter() {
        var _1, i, x = 0, y = 0, _5 = this.points.length;
        for (i = 0; i < _5; ++i) {
            _1 = this.points[i];
            x += _1[0];
            y += _1[1]
        }
        x = Math.round(x / _5);
        y = Math.round(y / _5);
        return[x, y]
    }
    , isc.A.setPoints = function isc_DrawPath_setPoints(_1) {
        this.points = _1;
        if (this.drawingVML) {
            this.$44u.points.value = this.getPointsText()
        } else if (this.drawingSVG) {
            this.$44z.setAttributeNS(null, "points", this.getPointsText())
        } else if (this.drawingBitmap) {
            this.drawPane.redrawBitmap()
        }
    }
    , isc.A.moveTo = function isc_DrawPath_moveTo(_1, _2) {
        var _3 = _1 - this.points[0][0];
        var _4 = _2 - this.points[0][1];
        this.moveBy(_3, _4)
    }
    , isc.A.moveBy = function isc_DrawPath_moveBy(_1, _2) {
        if (this.getClass().getClassName() !== 'DrawLinePath') {
            var _3, _4 = this.points.length;
            for (var i = 0; i < _4; ++i) {
                _3 = this.points[i];
                _3[0] += _1;
                _3[1] += _2
            }
            if (this.drawingVML) {
                this.$44u.points.value = this.getPointsText()
            } else if (this.drawingBitmap) {
                this.drawPane.redrawBitmap()
            }
        }
        this.Super("moveBy", arguments)
    }
    );
    isc.B._maxIndex = isc.C + 9;
    isc.defineClass("DrawTriangle", "DrawPath");
    isc.A = isc.DrawTriangle.getPrototype();
    isc.B = isc._allFuncs;
    isc.C = isc.B._maxIndex;
    isc.D = isc._funcClasses;
    isc.D[isc.C] = isc.A.Class;
    isc.A.points = [[0, 0], [50, 50], [100, 0]];
    isc.A.lineCap = "butt";
    isc.A.svgElementName = "path";
    isc.A.vmlElementName = "POLYLINE";
    isc.B.push(isc.A.init = function isc_DrawTriangle_init() {
        this.Super("init")
    }
    , isc.A.getPointsText = function isc_DrawTriangle_getPointsText() {
        var _1 = isc.SB.create(), _2 = this.points;
        for (var i = 0; i < _2.length; i++) {
            _1.append(_2[i][0], " ", _2[i][1], " ")
        }
        _1.append(_2[0][0], " ", _2[0][1]);
        return _1.toString()
    }
    , isc.A.getAttributesSVG = function isc_DrawTriangle_getAttributesSVG() {
        var _1 = "d='M ", i, _3 = this.points[0];
        _1 += _3[0] + " " + _3[1] + " ";
        for (i = 1; i < this.points.length; ++i) {
            _3 = this.points[i];
            _1 += "L " + _3[0] + " " + _3[1]
        }
        _3 = this.points[0];
        _1 += "L " + _3[0] + " " + _3[1] + "'";
        return _1
    }
    , isc.A.drawBitmapPath = function isc_DrawTriangle_drawBitmapPath(_1) {
        var _2 = this.points;
        this.bmMoveTo(_2[0][0], _2[0][1], _1);
        for (var i = 1; i < this.points.length; i++) {
            var _4 = _2[i];
            this.bmLineTo(_4[0], _4[1], _1)
        }
        this.bmLineTo(_2[0][0], _2[0][1], _1);
        _1.closePath()
    }
    , isc.A.dragStart = function isc_DrawTriangle_dragStart(_1, _2) {
        var _3 = this.getBoundingBox();
        var _4 = _3[0] + (_3[2] - _3[0]) / 2;
        var _5 = _3[1];
        var _6 = this.drawPane.normalize(_1.x, _1.y);
        this.dragOffsetX = _6.x - this.drawPane.getPageLeft() - _4;
        this.dragOffsetY = _6.y - this.drawPane.getPageTop() - _5;
        return true
    }
    );
    isc.B._maxIndex = isc.C + 5;
    isc.defineClass("DrawLinePath", "DrawPath");
    isc.A = isc.DrawLinePath.getPrototype();
    isc.B = isc._allFuncs;
    isc.C = isc.B._maxIndex;
    isc.D = isc._funcClasses;
    isc.D[isc.C] = isc.A.Class;
    isc.A.startPoint = [0, 0];
    isc.A.endPoint = [100, 100];
    isc.A.tailSize = 30;
    isc.A.startArrow = null;
    isc.A.endArrow = "open";
    isc.A.showStartPointKnobs = isc.DrawLine.getPrototype().showStartPointKnobs;
    isc.A.hideStartPointKnobs = isc.DrawLine.getPrototype().hideStartPointKnobs;
    isc.A.showEndPointKnobs = isc.DrawLine.getPrototype().showEndPointKnobs;
    isc.A.hideEndPointKnobs = isc.DrawLine.getPrototype().hideEndPointKnobs;
    isc.A.updateControlKnobs = isc.DrawLine.getPrototype().updateControlKnobs;
    isc.B.push(isc.A.init = function isc_DrawLinePath_init() {
        this.startLeft = this.startLeft || this.startPoint[0];
        this.startTop = this.startTop || this.startPoint[1];
        this.endLeft = this.endLeft || this.endPoint[0];
        this.endTop = this.endTop || this.endPoint[1];
        this.points = this.$45e();
        this.Super("init")
    }
    , isc.A.$45e = function isc_DrawLinePath__getSegmentPoints() {
        var _1 = this.tailSize;
        if (this.startLeft <= this.endLeft) {
            _1 = -_1
        }
        this.$45f = [];
        this.$45f.addList([[this.startLeft, this.startTop], [this.startLeft - _1, this.startTop], [this.endLeft + _1, this.endTop], [this.endLeft, this.endTop]]);
        return this.$45f
    }
    , isc.A.$63r = function isc_DrawLinePath__drawLineStartArrow() {
        return this.startArrow == "open"
    }
    , isc.A.$63s = function isc_DrawLinePath__drawLineEndArrow() {
        return this.endArrow == "open"
    }
    , isc.A.getCenter = function isc_DrawLinePath_getCenter() {
        return[this.startPoint[0] + Math.round((this.endPoint[0] - this.startPoint[0]) / 2), this.startPoint[1] + Math.round((this.endPoint[1] - this.startPoint[1]) / 2)]
    }
    , isc.A.dragStart = function isc_DrawLinePath_dragStart(_1, _2) {
        var _3 = this.getBoundingBox();
        var _4 = _3[0];
        var _5 = _3[1];
        var _6 = this.drawPane.normalize(_1.x, _1.y);
        this.dragOffsetX = _6.x - this.drawPane.getPageLeft() - _4;
        this.dragOffsetY = _6.y - this.drawPane.getPageTop() - _5;
        return true
    }
    , isc.A.setStartPoint = function isc_DrawLinePath_setStartPoint(_1, _2) {
        if (_1 != null) {
            this.startLeft = this.startPoint[0] = _1
        }
        if (_2 != null) {
            this.startTop = this.startPoint[1] = _2
        }
        this.setPoints(this.$45e());
        this.updateControlKnobs()
    }
    , isc.A.setEndPoint = function isc_DrawLinePath_setEndPoint(_1, _2) {
        if (_1 != null) {
            this.endLeft = this.endPoint[0] = _1
        }
        if (_2 != null) {
            this.endTop = this.endPoint[1] = _2
        }
        this.setPoints(this.$45e());
        this.updateControlKnobs()
    }
    , isc.A.drawBitmapPath = function isc_DrawLinePath_drawBitmapPath(_1) {
        var _2 = 10;
        if (this.startLeft > this.endLeft)
            _2 = -_2;
        if (this.startArrow == "open") {
            this.bmMoveTo(this.startLeft + _2, this.startTop - _2, _1);
            this.bmLineTo(this.startLeft, this.startTop, _1);
            this.bmLineTo(this.startLeft + _2, this.startTop + _2, _1)
        }
        var _3 = this.points;
        this.bmMoveTo(_3[0][0], _3[0][1], _1);
        for (var i = 1; i < _3.length; i++) {
            var _5 = _3[i];
            if (this.linePattern.toLowerCase() !== "solid") {
                this.$85x(_3[i - 1][0], _3[i - 1][1], _3[i][0], _3[i][1], _1)
            } else {
                this.bmLineTo(_5[0], _5[1], _1)
            }
        }
        if (this.endArrow == "open") {
            this.bmMoveTo(this.endLeft - _2, this.endTop + _2, _1);
            this.bmLineTo(this.endLeft, this.endTop, _1);
            this.bmLineTo(this.endLeft - _2, this.endTop - _2, _1)
        }
    }
    , isc.A.getBoundingBox = function isc_DrawLinePath_getBoundingBox() {
        return[].concat(this.startPoint, this.endPoint)
    }
    , isc.A.moveBy = function isc_DrawLinePath_moveBy(_1, _2) {
        this.startLeft += _1;
        this.startPoint[0] += _1;
        this.startTop += _2;
        this.startPoint[1] += _2;
        this.endLeft += _1;
        this.endPoint[0] += _1;
        this.endTop += _2;
        this.endPoint[1] += _2;
        this.setPoints(this.$45e());
        this.Super("moveBy", arguments);
        this.moved(_1, _2)
    }
    , isc.A.moveTo = function isc_DrawLinePath_moveTo(_1, _2) {
        this.moveBy(_1 - this.startLeft, _2 - this.startTop);
        this.moved(_1, _2)
    }
    );
    isc.B._maxIndex = isc.C + 12;
    isc.GraphMath = {zeroPoint: [0, 0], polar2screen: function(_1, _2, _3, _4) {
            _3 = _3 || this.zeroPoint;
            var _5 = Math.PI - ((_1 + 450) % 360) / 180 * Math.PI;
            var _6 = [_3[0] + (_2 * Math.cos(_5)), _3[1] + (-_2 * Math.sin(_5))]
            if (_4) {
                _6[0] = Math.round(_6[0]);
                _6[1] = Math.round(_6[1])
            }
            return _6
        }, screen2polar: function(_1, _2) {
            return[((Math.PI - Math.atan2(-_2, _1)) / Math.PI * 180 + 270) % 360, Math.sqrt(Math.pow(_1, 2) + Math.pow(_2, 2))]
        }, angleDifference: function(_1, _2) {
            var _3 = Math.max(_1, _2), _4 = Math.min(_1, _2), _5 = _3 - _4, _6 = _4 + 360 - _3;
            if (_6 < _5) {
                var _7 = _3 == _1 ? 1 : -1;
                return _7 * _6
            } else {
                var _7 = _3 == _1 ? -1 : 1;
                return _7 * _5
            }
        }, straightDistance: function(_1, _2) {
            var _3 = _1[0] - _2[0], _4 = _1[1] - _2[1];
            return Math.sqrt(_3 * _3 + _4 * _4)
        }, trimLineEnds: function(_1, _2, _3, _4, _5, _6) {
            var _7 = Math.sqrt(Math.pow(_3 - _1, 2) + Math.pow(_4 - _2, 2));
            if (_5 + _6 > _7) {
                var _8 = _5 / (_5 + _6) * (_3 - _1) + _1;
                var _9 = _5 / (_5 + _6) * (_4 - _2) + _2;
                return[_8 + (_1 == _3 ? 0 : _1 > _3 ? 0.01 : -0.01), _9 + (_2 == _4 ? 0 : _2 > _4 ? 0.01 : -0.01), _8 + (_1 == _3 ? 0 : _1 > _3 ? -0.01 : 0.01), _9 + (_2 == _4 ? 0 : _2 > _4 ? -0.01 : 0.01)]
            }
            var _10 = Math.atan2(_2 - _4, _3 - _1);
            return[_1 + (_5 * Math.cos(_10)), _2 - (_5 * Math.sin(_10)), _3 - (_6 * Math.cos(_10)), _4 + (_6 * Math.sin(_10))]
        }}
    isc.A = isc.Canvas.getPrototype();
    isc.B = isc._allFuncs;
    isc.C = isc.B._maxIndex;
    isc.D = isc._funcClasses;
    isc.D[isc.C] = isc.A.Class;
    isc.B.push(isc.A.setCenter = function isc_Canvas_setCenter(_1, _2) {
        this.moveTo(_1 - this.getVisibleWidth() / 2, _2 - this.getVisibleHeight() / 2)
    }
    , isc.A.getCenter = function isc_Canvas_getCenter() {
        return[this.getLeft() + this.getVisibleWidth() / 2, this.getTop() + this.getVisibleHeight() / 2]
    }
    );
    isc.B._maxIndex = isc.C + 2;
    isc.defineClass("LinkedList", "Class");
    isc.A = isc.LinkedList.getPrototype();
    isc.B = isc._allFuncs;
    isc.C = isc.B._maxIndex;
    isc.D = isc._funcClasses;
    isc.D[isc.C] = isc.A.Class;
    isc.A.length = 0;
    isc.B.push(isc.A.init = function isc_LinkedList_init() {
        this.head = null
    }
    , isc.A.add = function isc_LinkedList_add(_1) {
        var _2 = {next: null, data: _1};
        if (!this.head) {
            this.head = _2
        } else {
            var _3 = this.head;
            while (_3.next) {
                _3 = _3.next
            }
            _3.next = _2
        }
        this.length++
    }
    , isc.A.get = function isc_LinkedList_get(_1) {
        var _2 = null;
        if (_1 >= 0 && _1 < this.length) {
            var _3 = this.head, i = 0;
            while (i++ < _1) {
                _3 = _3 && _3.next
            }
            _2 = _3.data
        }
        return _2
    }
    , isc.A.remove = function isc_LinkedList_remove(_1) {
        var _2 = null;
        if (_1 >= 0 && _1 < this.length) {
            var _3 = this.head, i = 0;
            if (_1 === 0) {
                this.head = _3.next
            } else {
                var _5;
                while (i++ < _1) {
                    _5 = _3;
                    _3 = _3 && _3.next
                }
                _5.next = _3.next
            }
            _2 = _3.data;
            this.length--
        }
        return _2
    }
    , isc.A.toArray = function isc_LinkedList_toArray() {
        var _1 = [], _2 = this.head;
        while (_2) {
            _1.push(_2.data);
            _2 = _2.next
        }
        return _1
    }
    );
    isc.B._maxIndex = isc.C + 5;
    isc.defineClass("QuadTree", "Class");
    isc.A = isc.QuadTree.getPrototype();
    isc.B = isc._allFuncs;
    isc.C = isc.B._maxIndex;
    isc.D = isc._funcClasses;
    isc.D[isc.C] = isc.A.Class;
    isc.A.depth = 0;
    isc.A.maxDepth = 0;
    isc.A.maxChildren = 0;
    isc.B.push(isc.A.init = function isc_QuadTree_init() {
        this.root = null;
        this.bounds = null
    }
    , isc.A.insert = function isc_QuadTree_insert(_1) {
        if (isc.isAn.Array(_1)) {
            var _2 = _1.length;
            for (var i = 0; i < _2; i++) {
                this.root.insert(_1[i])
            }
        } else {
            this.root.insert(_1)
        }
    }
    , isc.A.remove = function isc_QuadTree_remove(_1) {
        this.root.remove(_1)
    }
    , isc.A.clear = function isc_QuadTree_clear() {
        this.root.clear()
    }
    , isc.A.retrieve = function isc_QuadTree_retrieve(_1) {
        return this.root.retrieve(_1).slice()
    }
    , isc.A.update = function isc_QuadTree_update(_1) {
        return this.root.update(_1)
    }
    );
    isc.B._maxIndex = isc.C + 6;
    isc.defineClass("QuadTreeNode", "Class");
    isc.A = isc.QuadTreeNode;
    isc.A.TOP_LEFT = 0;
    isc.A.TOP_RIGHT = 1;
    isc.A.BOTTOM_LEFT = 2;
    isc.A.BOTTOM_RIGHT = 3;
    isc.A = isc.QuadTreeNode.getPrototype();
    isc.B = isc._allFuncs;
    isc.C = isc.B._maxIndex;
    isc.D = isc._funcClasses;
    isc.D[isc.C] = isc.A.Class;
    isc.A.depth = 0;
    isc.A.maxDepth = 4;
    isc.A.maxChildren = 4;
    isc.B.push(isc.A.init = function isc_QuadTreeNode_init() {
        this.bounds = null;
        this.nodes = [];
        this.children = isc.LinkedList.create()
    }
    , isc.A.insert = function isc_QuadTreeNode_insert(_1) {
        if (this.nodes.length) {
            var _2 = this.findQuadrants(_1);
            for (var i = 0; i < _2.length; ++i) {
                var _4 = _2[i];
                this.nodes[_4].insert(_1)
            }
            return
        }
        this.children.add(_1);
        var _5 = this.children.length;
        if (!(this.depth >= this.maxDepth) && _5 > this.maxChildren) {
            this.subdivide();
            for (var i = 0; i < _5; i++) {
                this.insert(this.children.get(i))
            }
            this.children = isc.LinkedList.create()
        }
    }
    , isc.A.remove = function isc_QuadTreeNode_remove(_1) {
        if (this.nodes.length) {
            var _2 = this.findQuadrants(_1);
            for (var i = 0; i < _2.length; ++i) {
                var _4 = _2[i];
                this.nodes[_4].remove(_1)
            }
        }
        for (var j = 0; j < this.children.length; ++j) {
            var _6 = this.children.get(j);
            if (_6 === _1) {
                this.children.remove(j)
            }
        }
    }
    , isc.A.retrieve = function isc_QuadTreeNode_retrieve(_1) {
        if (this.nodes.length) {
            var _2 = this.findQuadrant(_1);
            return this.nodes[_2].retrieve(_1)
        }
        return this.children.toArray()
    }
    , isc.A.findQuadrant = function isc_QuadTreeNode_findQuadrant(_1) {
        var b = this.bounds;
        var _3 = (_1.x > b.x + b.width / 2) ? false : true;
        var _4 = (_1.y > b.y + b.height / 2) ? false : true;
        var _5 = isc.QuadTreeNode.TOP_LEFT;
        if (_3) {
            if (!_4) {
                _5 = isc.QuadTreeNode.BOTTOM_LEFT
            }
        } else {
            if (_4) {
                _5 = isc.QuadTreeNode.TOP_RIGHT
            } else {
                _5 = isc.QuadTreeNode.BOTTOM_RIGHT
            }
        }
        return _5
    }
    , isc.A.findQuadrants = function isc_QuadTreeNode_findQuadrants(_1) {
        var _2 = [];
        var _3 = {};
        var _4 = this.findQuadrant({x: _1.x, y: _1.y});
        _2.push(_4);
        _3[_4] = true;
        _4 = this.findQuadrant({x: _1.x + _1.width, y: _1.y + _1.height});
        if (!_3[_4]) {
            _2.push(_4);
            _3[_4] = true
        }
        _4 = this.findQuadrant({x: _1.x, y: _1.y + _1.height});
        if (!_3[_4]) {
            _2.push(_4);
            _3[_4] = true
        }
        _4 = this.findQuadrant({x: _1.x + _1.width, y: _1.y});
        if (!_3[_4]) {
            _2.push(_4)
        }
        return _2
    }
    , isc.A.subdivide = function isc_QuadTreeNode_subdivide() {
        var _1 = this.depth + 1;
        var _2 = this.bounds.x;
        var _3 = this.bounds.y;
        var _4 = (this.bounds.width / 2) | 0;
        var _5 = (this.bounds.height / 2) | 0;
        var _6 = _2 + _4;
        var _7 = _3 + _5;
        this.nodes[isc.QuadTreeNode.TOP_LEFT] = isc.QuadTreeNode.create({depth: _1});
        this.nodes[isc.QuadTreeNode.TOP_LEFT].bounds = {x: _2, y: _3, width: _4, height: _5};
        this.nodes[isc.QuadTreeNode.TOP_RIGHT] = isc.QuadTreeNode.create({depth: _1});
        this.nodes[isc.QuadTreeNode.TOP_RIGHT].bounds = {x: _6, y: _3, width: _4, height: _5};
        this.nodes[isc.QuadTreeNode.BOTTOM_LEFT] = isc.QuadTreeNode.create({depth: _1});
        this.nodes[isc.QuadTreeNode.BOTTOM_LEFT].bounds = {x: _2, y: _7, width: _4, height: _5};
        this.nodes[isc.QuadTreeNode.BOTTOM_RIGHT] = isc.QuadTreeNode.create({depth: _1});
        this.nodes[isc.QuadTreeNode.BOTTOM_RIGHT].bounds = {x: _6, y: _7, width: _4, height: _5}
    }
    , isc.A.clear = function isc_QuadTreeNode_clear() {
        this.children = isc.LinkedList.create();
        var _1 = this.nodes.length;
        for (var i = 0; i < _1; i++) {
            this.nodes[i].clear()
        }
        this.nodes = [];
        this.depth = 0;
        this.maxDepth = 4;
        this.maxChildren = 4
    }
    , isc.A.update = function isc_QuadTreeNode_update(_1) {
        this.remove(_1);
        this.insert(_1)
    }
    );
    isc.B._maxIndex = isc.C + 9;
    isc.defineClass("DrawKnob", "Canvas");
    isc.A = isc.DrawKnob.getPrototype();
    isc.B = isc._allFuncs;
    isc.C = isc.B._maxIndex;
    isc.D = isc._funcClasses;
    isc.D[isc.C] = isc.A.Class;
    isc.A.width = 10;
    isc.A.height = 10;
    isc.A.overflow = "hidden";
    isc.A.cursor = "crosshair";
    isc.A.canDrag = true;
    isc.A.dragAppearance = "target";
    isc.A.dragMove = "return this.$64f()";
    isc.A.dragStop = "return this.$64f()";
    isc.A.dragAppearance = "none";
    isc.A.keepInParentRect = true;
    isc.A.autoDraw = false;
    isc.A.knobShapeDefaults = {_constructor: isc.DrawOval, radius: 5, lineWidth: 2, fillColor: "#FF0000", fillOpacity: 0.5, autoDraw: true, updateControlKnobs: function() {
            return
        }, erase: function() {
            if (this.erasing)
                return;
            this.erasing = true;
            if (this.creator.isDrawn())
                this.creator.clear();
            this.Super("erase", arguments);
            delete this.erasing
        }};
    isc.B.push(isc.A.initWidget = function isc_DrawKnob_initWidget() {
        this.left = this.x - this.width / 2;
        this.top = this.y - this.height / 2
    }
    , isc.A.setCenterPoint = function isc_DrawKnob_setCenterPoint(_1, _2, _3) {
        if (isc.isAn.Array(_1)) {
            _2 = _1[1];
            _1 = _1[0]
        }
        var _4, _5;
        if (_3) {
            var _6 = this.drawPane.drawing2screen([_1 - this.$44g / 2, _2 - this.$44h / 2, 0, 0]);
            _4 = _6[0], _5 = _6[1]
        } else {
            _4 = _1 - this.width / 2;
            _5 = _2 - this.height / 2
        }
        this.moveTo(_4, _5)
    }
    , isc.A.draw = function isc_DrawKnob_draw() {
        var _1 = (this.$907);
        if (!this.knobShape) {
            this.knobShape = this.createAutoChild("knobShape", {drawPane: this.drawPane, centerPoint: [this.x, this.y]});
            this.$907 = true;
            ;
            this.drawPane.addCanvasItem(this);
            delete this.$907
        }
        if (!_1)
            return this.Super("draw", arguments)
    }
    , isc.A.moved = function isc_DrawKnob_moved() {
        if (!this.synchingToPane)
            this.updateKnobShape()
    }
    , isc.A.$64f = function isc_DrawKnob__updatePoints() {
        var _1 = isc.EH.getX() -
                (this.drawPane.getPageLeft() + this.drawPane.getLeftMargin() + this.drawPane.getLeftBorderSize()), _2 = isc.EH.getY() -
                (this.drawPane.getPageTop() + this.drawPane.getTopMargin() + this.drawPane.getTopBorderSize());
        var _3 = this.drawPane.screen2drawing([_1, _2, 0, 0]);
        var _4 = _3[0], _5 = _3[1], _6 = _4 - (this.$44e + this.$44g / 2), _7 = _5 - (this.$44f + this.$44h / 2);
        this.updatePoints(_4, _5, _6, _7)
    }
    , isc.A.updateKnobShape = function isc_DrawKnob_updateKnobShape() {
        var _1 = this.drawPane.screen2drawing([this.getLeft() + this.getWidth() / 2, this.getTop() + this.getHeight() / 2, 0, 0]);
        var x = _1[0], y = _1[1], _4 = x - this.$44g / 2, _5 = y - this.$44h / 2;
        this.$44e = _4, this.$44f = _5;
        this.knobShape.setCenterPoint(x, y)
    }
    , isc.A.updatePoints = function(x, y, dX, dY) {
    }
    , isc.A.clear = function isc_DrawKnob_clear() {
        this.Super("clear", arguments);
        this.knobShape.erase()
    }
    );
    isc.B._maxIndex = isc.C + 8;
    isc.DrawKnob.registerStringMethods({updatePoints: "x,y,dX,dY"});
    isc._moduleEnd = isc._Drawing_end = (isc.timestamp ? isc.timestamp() : new Date().getTime());
    if (isc.Log && isc.Log.logIsInfoEnabled('loadTime'))
        isc.Log.logInfo('Drawing module init time: ' + (isc._moduleEnd - isc._moduleStart) + 'ms', 'loadTime');
    delete isc.definingFramework;
} else {
    if (window.isc && isc.Log && isc.Log.logWarn)
        isc.Log.logWarn("Duplicate load of module 'Drawing'.");
}
/*
 * Isomorphic SmartClient
 * Version v8.2p_2012-06-03 (2012-06-03)
 * Copyright(c) 1998 and beyond Isomorphic Software, Inc. All rights reserved.
 * "SmartClient" is a trademark of Isomorphic Software, Inc.
 *
 * licensing@smartclient.com
 *
 * http://smartclient.com/license
 */

