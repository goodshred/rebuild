// common Init
$(function(){
	let t = $('.rb-scroller');
	t.perfectScrollbar()
	$(window).resize(function(){
		$setTimeout(function(){
			t.perfectScrollbar('update')
		}, 500, 'rb-scroller-update');
	});
	
	// tooltip
	$('[data-toggle="tooltip"]').tooltip()
	
	if ($('body.dialog').length == 0 && $('body.view-body').length == 0){
		console.log('In top-frame ... ' + location.href)
		$('.sidebar-elements li').each(function(){
			let _this = $(this)
			if (!_this.hasClass('divider')) _this.tooltip({ placement: 'right', title: _this.text().trim(), delay: 200 })
		})
		__initNavs()
	}
	
	// animate class
	$setTimeout(function(){
		$(document.body).addClass('rb-animate')
	}, 1000)
	
	if (rb.isAdminUser == true) {
		$('.J_for-admin').removeClass('hide')
		if (location.href.indexOf('/admin/') == -1) {
			if ($('.J_admin-settings').data('verified') == true) {
				$('.J_admin-settings a i').addClass('text-danger')
			}
		}
	} else {
		$('.J_for-admin').remove()
	}
});

const __initNavs = function(){
	// Nav
	$('.rb-toggle-left-sidebar').click(function(){
		let el = $('.rb-collapsible-sidebar').toggleClass('rb-collapsible-sidebar-collapsed')
		let collapsed = el.hasClass('rb-collapsible-sidebar-collapsed')
		$storage.set('rb-sidebar-collapsed', collapsed)
		$('.sidebar-elements li').tooltip('toggleEnabled')
	});
	if ($storage.get('rb-sidebar-collapsed') == 'true'){
		$('.rb-collapsible-sidebar').addClass('rb-collapsible-sidebar-collapsed')
	} else {
		$('.sidebar-elements li').tooltip('disable')
	}
	
	// At small-width
	$('.left-sidebar-toggle').click(function(){
		$('.rb-collapsible-sidebar').toggleClass('rb-collapsible-sidebar-collapsed')
		$('.left-sidebar-spacer').toggleClass('open')
	}).text($('.rb-right-navbar .page-title').text())
	
	// aside
	let aside = $('.page-aside')
	if (aside.length > 0) {
		$('.page-aside .aside-header').click(function(){
			$(this).toggleClass('collapsed')
			$('.page-aside .aside-nav').toggleClass('show')
		})
	}
	
	$('.nav-settings').click(function(){
		window.__currentModal = rb.modal(rb.baseUrl + '/page/settings/nav-settings', '设置导航菜单');
	});
}

// 关闭当前打开的 Modal
var $hideModal = function() {
	if (window.__currentModal) window.__currentModal.hide()
}

// 计算分页
// @tp 总计页面 
// @cp 当前页面
const $calcPages = function(tp, cp){
	tp = ~~tp; cp = ~~cp;
	let pages = [];
	if (tp <= 8){
		for (var i = 1; i <= tp; i++) pages.push(i);
		return pages;
	}
	if (cp > tp) cp = tp;
	if (cp <= 4) cp = 4;
	var begin = cp - 2, end = cp + 3;
	if (begin < 1) begin = 1;
	if (end > tp) end = tp;
	if (begin > 1) pages.push(1);
	if (begin > 2) pages.push('.');
	for (var i = begin; i < end; i++) pages.push(i);
	if (end <= tp - 1) pages.push('.');
	if (end <= tp) pages.push(tp);
	return pages;
}

// @mbg = .btn-group
const $cleanMenu = function(mbg){
	mbg = $(mbg)
	let mbgMenu = mbg.find('.dropdown-menu')
	let first = mbgMenu.children().first()
	if (first.hasClass('dropdown-divider')) first.remove()
	let last = mbgMenu.children().last()
	if (last.hasClass('dropdown-divider')) last.remove()
	
	$(mbgMenu.children()).each(function(){
		let item = $(this)
		if (item.hasClass('hide')) item.remove()
	})
	
	// remove btn
	if (mbgMenu.children().length == 0){
		mbg.remove()
	}
}