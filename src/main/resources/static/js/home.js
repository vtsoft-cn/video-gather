String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
}
String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
const MAC = {
    'Url': document.URL,
    'Title': document.title,
    'UserAgent': function () {
        var ua = navigator.userAgent;//navigator.appVersion
        return {
            'mobile': !!ua.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
            'ios': !!ua.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
            'android': ua.indexOf('Android') > -1 || ua.indexOf('Linux') > -1, //android终端或者uc浏览器
            'iPhone': ua.indexOf('iPhone') > -1 || ua.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
            'iPad': ua.indexOf('iPad') > -1, //是否iPad
            'trident': ua.indexOf('Trident') > -1, //IE内核
            'presto': ua.indexOf('Presto') > -1, //opera内核
            'webKit': ua.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
            'gecko': ua.indexOf('Gecko') > -1 && ua.indexOf('KHTML') === -1, //火狐内核
            'weixin': ua.indexOf('MicroMessenger') > -1 //是否微信 ua.match(/MicroMessenger/i) == "micromessenger",
        };
    }(),
    'Copy': function (s) {
        if (window.clipboardData) {
            window.clipboardData.setData("Text", s);
        } else {
            if ($("#mac_flash_copy").get(0) === undefined) {
                $('<div id="mac_flash_copy"></div>');
            } else {
                $('#mac_flash_copy').html('');
            }
            $('#mac_flash_copy').html('<embed src=' + SitePath + '"images/_clipboard.swf" FlashVars="clipboard=' + escape(s) + '" width="0" height="0" type="application/x-shockwave-flash"></embed>');
        }
        layer.msg("复制成功")
    },
    'Home': function (o, u) {
        try {
            o.style.behavior = 'url(#default#homepage)';
            o.setHomePage(u);
        } catch (e) {
            if (window.netscape) {
                try {
                    netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
                } catch (e) {
                    layer.msg("此操作被浏览器拒绝！请手动设置");
                }
                var moz = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
                moz.setCharPref('browser.startup.homepage', u);
            }
        }
    },
    'Fav': function (u, s) {
        try {
            window.external.addFavorite(u, s);
        } catch (e) {
            try {
                window.sidebar.addPanel(s, u, "");
            } catch (e) {
                layer.msg("加入收藏出错，请使用键盘Ctrl+D进行添加");
            }
        }
    },
    'Open': function (u, w, h) {
        window.open(u, 'macopen1', 'toolbars=0, scrollbars=0, location=0, statusbars=0,menubars=0,resizable=yes,width=' + w + ',height=' + h + '');
    },
    'Cookie': {
        'Set': function (name, value, days) {
            var exp = new Date();
            exp.setTime(exp.getTime() + days * 24 * 60 * 60 * 1000);
            var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
            document.cookie = name + "=" + encodeURIComponent(value) + ";path=/;expires=" + exp.toUTCString();
        },
        'Get': function (name) {
            var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
            if (arr != null) {
                return decodeURIComponent(arr[2]);
                return null;
            }
        },
        'Del': function (name) {
            var exp = new Date();
            exp.setTime(exp.getTime() - 1);
            var cval = this.Get(name);
            if (cval != null) {
                document.cookie = name + "=" + encodeURIComponent(cval) + ";path=/;expires=" + exp.toUTCString();
            }
        }
    },
    'GoBack': function () {
        const ldghost = document.domain;
        if (document.referrer.indexOf(ldghost) > 0) {
            history.back();
        } else {
            window.location = "http://" + ldghost;
        }
    },
    // 'Adaptive':function(){
    //     if(maccms.mob_status==='1'&& maccms.url !== maccms.wapurl){
    //         if(document.domain ===maccms.url && MAC.UserAgent.mobile){
    //             location.href = location.href.replace(maccms.url,maccms.wapurl);
    //         }
    //         else if(document.domain ===maccms.wapurl && !MAC.UserAgent.mobile){
    //             location.href = location.href.replace(maccms.wapurl,maccms.url);
    //         }
    //     }
    // },
    'CheckBox': {
        'All': function (n) {
            $("input[name='" + n + "']").each(function () {
                this.checked = true;
            });
        },
        'Other': function (n) {
            $("input[name='" + n + "']").each(function () {
                this.checked = !this.checked;
            });
        },
        'Count': function (n) {
            let res = 0;
            $("input[name='" + n + "']").each(function () {
                if (this.checked) {
                    res++;
                }
            });
            return res;
        },
        'Ids': function (n) {
            const res = [];
            $("input[name='" + n + "']").each(function () {
                if (this.checked) {
                    res.push(this.value);
                }
            });
            return res.join(",");
        }
    },
    'Image': {
        'Lazyload': {
            'Show': function () {
                try {
                    $("img.lazy").lazyload();
                } catch (e) {
                }
            },
            'Box': function ($id) {
                $("img.lazy").lazyload({
                    container: $("#" + $id)
                });
            }
        }
    },
    'Verify': {
        'Init': function () {
            MAC.Verify.Focus();
            MAC.Verify.Click();
        },
        'Focus': function () {//验证码框焦点
            $('body').on("focus", ".mac_verify", function () {
                $(this).removeClass('mac_verify').after(MAC.Verify.Show());
                $(this).unbind();
            });
        },
        'Click': function () {//点击刷新
            $('body').on('click', 'img.mac_verify_img', function () {
                $(this).attr('src', maccms.path + '/index.php/verify/index.html?');
            });
        },
        'Refresh': function () {
            $('.mac_verify_img').attr('src', maccms.path + '/index.php/verify/index.html?');
        },
        'Show': function () {
            return '<img class="mac_verify_img" src="' + maccms.path + '/index.php/verify/index.html?"  title="看不清楚? 换一张！" alt="">';
        }
    },
    'Search': {
        'Init': function () {
            $('.mac_search').click(function () {
                const that = $(this);
                const url = that.attr('data-href') ? that.attr('data-href') : maccms.path + '/index.php/vod/search.html';
                location.href = url + '?wd=' + encodeURIComponent($("#wd").val());
            });
        },
        'Submit': function () {

            return false;
        }
    },
    'Pop': {
        'Show': function ($url, $callback) {
            $.ajax({
                type: 'post',
                url: $url,
                timeout: 3000,
                error: function () {
                    $('body').append(layer.msg("加载失败，请刷新"));
                },
                success: function ($r) {
                    $('body').append($r);
                    $callback($r);
                }
            });
        }
    },
    'AdsWrap': function (w, h, n) {
        document.writeln('<img width="' + w + '" height="' + h + '" alt="' + n + '" style="background-color: #CCCCCC" />');
    },
    'Css': function ($url) {
        $("<link>").attr({rel: "stylesheet", type: "text/css", href: $url}).appendTo("head");
    },
    'Style': {
        'Init': function () {
            let style = MAC.Cookie.Get('mx_style');
            let currentBgColor = $("#cssFile").attr('href').match(/\w+.css$/g)[0].split('.')[0]
            if (style != null) {
                this.Css();
            } else {
                MAC.Cookie.Set('mx_style', currentBgColor);
            }
            if (currentBgColor === 'black') {
                $('#changeAppearance').html("切换浅色外观")
            } else {
                $('#changeAppearance').html("切换深色外观")
            }
            this.Switch();
        },
        'Set': function (e) {
            MAC.Cookie.Set('mx_style', e);
        },
        'Switch': function () {
            let style = MAC.Cookie.Get('mx_style')
            if (style === 'black') {
                $('#changeAppearance').html("切换浅色外观")
            } else if (style === 'white') {
                $('#changeAppearance').html("切换深色外观")
            }
            $('.icon-rijian').click(function () {
                $('.icon-yejian').toggle()
                $('.icon-rijian').toggle()
                let id = $(this).attr('data-id');
                $('#changeAppearance').html("切换深色外观")
                MAC.Style.Set(id);
                MAC.Style.Css();
            })
            $('.icon-yejian').click(function () {
                $('.icon-yejian').toggle()
                $('.icon-rijian').toggle()
                let id = $(this).attr('data-id');
                $('#changeAppearance').html("切换浅色外观")
                MAC.Style.Set(id);
                MAC.Style.Css();
            })
        },
        'Css': function () {
            $("#cssFile").attr('href', '/css/' + MAC.Cookie.Get('mx_style') + '.css');
        }
    }
};

$(function () {
    //异步加载图片初始化
    MAC.Image.Lazyload.Show();
    //验证码初始化
    MAC.Verify.Init();
    //样式初始化
    MAC.Style.Init();
});
