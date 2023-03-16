package com.example.quick_cheque.expandable_list_items

import com.example.quick_cheque.model.Cheque

class ChequeListItem(
    val cheque: Cheque,
    var isExpanded: Boolean
) : ListItem {}