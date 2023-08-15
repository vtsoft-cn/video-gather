$(function () {
    $('#sortBtn').click(function () {
        let index = 0
        $('.module-list').each(function (listIndex, val) {
            if ($(val).hasClass('active')) {
                index = listIndex
            }
        })
        let templateHtml = []
        $('.module-list').eq(index).find('.module-play-list-link').each(function (linkIndex, val) {
            templateHtml.push(val)
        })
        let reverseTemplateHtml = templateHtml.reverse()
        $('.module-list').eq(index).find('.module-play-list-content').html(reverseTemplateHtml)
    })
})

function show_history(cookie_name) {
    var history = $.cookie("mac_history_mxpro");
    var history_data = [];
    var history_html = '';
    if ((history != undefined) && (history != '')) {
        history_data = eval(history);
    }
    if (history_data.length > 0) {
        for (var $i = 0; $i < history_data.length; $i++) {
            history_html += '<li class="drop-item drop-item-content"><a href="' + history_data[$i].vod_url + '" title="' + history_data[$i].vod_name + '" class="drop-item-link"><span>' + history_data[$i].vod_part + '</span>' + history_data[$i].vod_name + '</a></li>';
            historyclean_html = '<li class="drop-item-op"><a href="" class="historyclean">清空</a></li>>';
        }
    } else {
        history_html = '<li class="drop-item drop-item-content nolist"><div class="drop-prompt">暂无观看影片的记录</div></li>';
    }
    $(".historical").append(history_html);
    try {
        $(".historical").append(historyclean_html);
    } catch (e) { }
}

$(function () {

    $('#y-first-name').html($('#y-playList').find('.tab-item').eq(0).children('span').text())

    $(".search-input").click(function () {
        $(".homepage,.searchbar-main").addClass("open");
    });

    $(".header-op-search").click(function () {
        $(".homepage,.searchbar-main").addClass("open");
        $(".page,.searchbar-main").addClass("open");
    });
    $(document).click(function (e) {
        if (($(e.target).closest(".search-input").length == 0 || $(e.target).closest(".cancel-btn").length != 0) && $(e.target).closest(".header-op-search").length == 0) {
            ($(e.target).closest(".header-op-search").length == 0)
            $(".homepage,.page,.searchbar-main").removeClass("open");
        }
    });
    show_history();
    $(".lazyload").lazyload({
        effect: "fadeIn",
        skip_invisible: false,
        event: 'sporty',
        threshold: 500
    });

    let index = $("#playSwiper .swiper-wrapper").find('.active').index()
    let navIndex = $(".navbar .swiper-wrapper").find('.active').index()
    const swiper = new Swiper('#playSwiper', {
        slidesPerView: 'auto',
        hashNavigation: true,
    })
    if (swiper.$el) {
        swiper.slideTo(index - 1)
    }
    const swiperNav = new Swiper('.navbar', {
        freeMode: true,
        slidesPerView: 'auto',
    })

    function initNavSwiper() {
        if (window.innerWidth < 559) {

            swiperNav.enable()
            if (navIndex > 4) {
                swiperNav.slideTo(navIndex - 1)
            }

            return

        } else {
            swiperNav.slideTo(0)
            swiperNav.disable()
        }
    }
    initNavSwiper()

    function retop() {
        let scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
        if (scrollTop >= 1000) {
            $('.retop').css('display', "flex")
        } else {
            $('.retop').hide()
        }
    }

    //回到顶部函数;
    $('.retop').click(function () {
        timer = setInterval(function () {
            let scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
            //让滚动条到顶部的距离自动缩减到0;
            document.documentElement.scrollTop = document.body.scrollTop = Math.floor(scrollTop - 100);//兼容性设置;
            if (scrollTop === 0 || document.documentElement.scrollTop === 0) {
                clearInterval(timer);
                setTimeout(() => { swiperNav.update(true) }, 150)
            }
        }, 10);
    })

    window.addEventListener('scroll', function () {
        retop()
        swiperNav.update(true)
    })
    window.addEventListener('resize', function () {
        initNavSwiper()
    })

    $('.module').each(function (index, el) {
        $(this).find('.tab-item').click(function () {
            if (!$(this).hasClass("active")) {
                $(this).addClass("active").siblings().removeClass("active");
                $(el).find(".tab-list").eq($(this).index()).addClass("active").siblings().removeClass("active");
                $(el).find(".tab-list").eq($(this).index()).find(".lazyload").lazyload();
            }
            $(".module-tab-drop").removeClass("module-tab-drop");
            $(this).parents(".module-tab-items").siblings(".module-tab-name").children(".module-tab-value").text($(this).attr("data-dropdown-value"));
        })
    })



    // 播放器选择源播放事件start
    $('#playSwiper').find('.tab-item').off('click')
    $('#playSwiper').find('.tab-item').click(function () {
        if (!$(this).hasClass("active")) {
            if ($(this).index() > 1) {
                console.log("右滑动", $(this).index())
                swiper.slideNext()
            } else {
                console.log("左滑动", $(this).index())
                swiper.slidePrev()
            }

            $(this).addClass("active").siblings().removeClass("active");
            $(".tab-list").eq($(this).index()).addClass("active").siblings().removeClass("active");
        }
        $(".module-tab-drop").removeClass("module-tab-drop");
        $(this).parents(".module-tab-items").siblings(".module-tab-name").children(".module-tab-value").text($(this).attr("data-dropdown-value"));
    })
    // 播放器选择源播放事件end 更多免费源码https://www.maccmsbox.com/

    $(".module-tab .module-tab-name").click(function () {
        $(this).parent(".module-tab").addClass("module-tab-drop");
        $(".module-tab-items").css("visibility", "visible");
        $(".swiper.module-tab-items").css("visibility", "");
        $(".module-tab-items-box").css("display", "");
    });
    $(".shortcuts-mobile-overlay,.close-drop").click(function () {
        $(".module-tab-drop").removeClass("module-tab-drop");
        $(".module-tab-items").removeAttr("style", "");
    });
    function show_tip() {
        layer.msg('视频地址已复制！', {time: 1500, shade: 0.8});
    }

    $(".historyclean").on("click", function () {
        $.cookie("mac_history_mxpro", null, { expires: -1, path: '/' });
    });
    $('.drop-qrcode-img').qrcode({
        text: location.href, //设置二维码内容，必须
        render: "canvas", //设置渲染方式 （有两种方式 table和canvas，默认是canvas）
        width: 265, //设置宽度
        height: 265, //设置高度
    });
    const clipboard = new ClipboardJS('.share-btn');
    clipboard.on('success', function (e) {
        show_tip('分享信息复制成功，赶紧去分享吧～');
    });
    clipboard.on('error', function (e) {
        console.log(e);
    });

    $(".module-heading-tab .module-heading-tab-link").click(function () {
        $(".module-heading-tab span").eq($(this).index()).addClass("active").siblings().removeClass('active');
        $(".tab-list").eq($(".module-heading-tab .module-heading-tab-link").index(this)).addClass("active").siblings().removeClass('active');
        $(".tab-list").eq($(".module-heading-tab .module-heading-tab-link").index(this)).find(".lazyload").lazyload();
    });
    const swiper2 = new Swiper('.swiper-small', {
        loop: true,
        allowTouchMove: false,
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
        pagination: {
            el: '.swiper-pagination',
            clickable: true
        },
    })
    const swiper1 = new Swiper('.swiper-big', {
        autoplay: {
            disableOnInteraction: false,
        },
        loop: true,
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
        pagination: {
            el: '.swiper-pagination',
            clickable: true
        },
        on: {
            slideChangeTransitionStart: function () {
                swiper2.slideTo(this.activeIndex)
            },
        }
    })

    let cookie = document.cookie
    const popup = document.querySelector('#popup')

    if (popup) {
        if (!cookie.includes('showBtn')) {
            popup.classList.add('popupShow')
        }
        popup.addEventListener('click', function () {
            let expires = new Date()
            expires.setTime(expires.getTime() + (1000 * 60 * 60 * 24))
            document.cookie = 'showBtn=true;expires=' + expires.toUTCString() + ';path=/'
            popup.remove()
        })
    }
});
