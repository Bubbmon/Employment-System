# -*- coding: utf-8 -*-
"""
@Author Legion
@Date 2021/6/21 10:12
@Description
"""

import pymysql
import xlrd
import sys


def load_data(type, fileName):
    conn = pymysql.connect(host='localhost', port=7123, database='employment',
                           user='root', passwd='1qaz@WSX', charset='utf8')
    book = xlrd.open_workbook(fileName)
    sheet = book.sheet_by_index(0)
    cur = conn.cursor()
    if type == 'info':
        sql = 'insert into info (title,content) values (%s,%s)'
    elif type == 'position':
        sql = 'insert into positionInfo (`position`,enterpriseId,title,content,salary,hrId,`degree`) values (%s,%s,%s,%s,%s,%s,%s)'
    else:
        print("This type is not supported: "+type)
        exit(1)
    for row in range(sheet.nrows):
        if row == 0:
            continue
        args = sheet.row_values(row)
        if type == 'info':
            args = args[0:2]
        cur.execute(sql, args)
    conn.commit()
    cur.close()
    conn.close()


if __name__ == '__main__':
    type = sys.argv[1]
    fileName = sys.argv[2]
    load_data(type, fileName)