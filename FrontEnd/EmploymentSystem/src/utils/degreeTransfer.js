const num2degree = {
    '0': '不限',
    '1': '高中',
    '2': '专科',
    '3': '本科',
    '4': '硕士',
    '5': '博士'
}

export function degreeTransfer(arg) {
    return {
        key: num2degree[arg],
    }
}