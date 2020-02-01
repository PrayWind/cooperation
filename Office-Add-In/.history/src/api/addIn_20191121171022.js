
new_element = document.createElement("script");
new_element.setAttribute("type","text/javascript");
new_element.setAttribute("src","../utils/request.js");
document.body.appendChild(new_element);

function login(username, password) {
    const data = {
        username, password
    }

    return request({
        url: '/login',
        method: 'post',
        data
    })
}