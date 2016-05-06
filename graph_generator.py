import pandas as pd
import networkx as nx
import csv

for file_num in xrange(0,100):
    
    '''Prepare variables on remote engines.'''
    # board_membership_list: [[(Name, Gender, DOB), InstitutionID], ...]
    board_membership_list = [[(s[4].decode('utf8'), s[5].decode('utf8'), s[6]), s[2]] for s in csv.reader(open('/tmp/network/BoardSimulation-'+str(file_num)+'.csv', 'r'), delimiter='	')][1:]
    uni_mem_list = list(set(s[0] for s in board_membership_list)) # Unique member list.
    uni_org_id_list = list(set(s[1] for s in board_membership_list)) # Unique instituion ID.

    '''mem_node_affi_list = [[(Name, Gender, DOB), Affiliation List], ...]'''
    mem_node_affi_list = []
    for uni_mem in uni_mem_list:
        affi_list = [s[1] for s in board_membership_list if s[0] == uni_mem]
        mem_node_affi_list.append([uni_mem, affi_list])

    # mem_elist = [(node1-(Name1, Gender1, DOB1), node2-(Name2, Gender2, DOB2), weight),...]
    mem_elist = []
    for node1_index in xrange(0, len(mem_node_affi_list)-1):
        for node2_index in xrange(node1_index+1, len(mem_node_affi_list)):
            affi_dif = len(mem_node_affi_list[node1_index][1]+mem_node_affi_list[node2_index][1])-len(set(mem_node_affi_list[node1_index][1]+mem_node_affi_list[node2_index][1]))
            if affi_dif != 0:
                mem_elist.append((mem_node_affi_list[node1_index][0], mem_node_affi_list[node2_index][0], affi_dif))

    ''' ====== Generate graphs ====== '''
    g_mem = nx.Graph()
    g_mem.add_weighted_edges_from(mem_elist)

    ''' ======== Write graphs to files ========'''
    nx.write_graphml(g_mem, '/tmp/graphs/simu-g_mem-'+str(file_num)+'.graphml', encoding='utf-8')