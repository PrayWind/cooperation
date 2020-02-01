import request from "../utils/request.js"
new_element = document.createElement("script");
new_element.setAttribute("type","text/javascript");
new_element.setAttribute("src","../utils/request.js");// 在这里引入了a.js
document.body.appendChild(new_element);

export function login(username, password) {
    const data = {
        username, password
    }

    return request({
        url: '/login',
        method: 'post',
        data
    })
}