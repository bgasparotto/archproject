/**
 * <p>
 * Main package of {@code web} layer. This package contains the web interfaces,
 * and the corresponding implementations can be found on its sub-packages.
 * </p>
 * <p>
 * The web layer is where the {@code MVC - Model-view-controller} pattern is
 * applied. The {@code web} layer is not supposed to have much business rules,
 * instead, it acts like a bridge between the views (JSPs, JSFs, HTML pages and
 * stuff) and the {@code service} layer.
 * </p>
 * <p>
 * The web layer should access resources from the {@code model} and
 * {@code service} layers, but shouldn't use resources from {@code persistence}
 * layer, even if it's possible to import or reference these resources, the
 * appropriate facade on service layer need to be provided instead of directly
 * accessing persistence resources.
 * </p>
 */
package com.bgasparotto.archproject.web;