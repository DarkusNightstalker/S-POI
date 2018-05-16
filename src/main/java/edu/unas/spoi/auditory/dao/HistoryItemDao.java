/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.auditory.dao;

import edu.unas.spoi.auditory.dao.interfac.IHistoryItemDao;
import edu.unas.spoi.auditory.model.HistoryItem;
import gkfire.hibernate.generic.GenericDao;

/**
 * The type History item dao.
 *
 * @author CTIC
 */
public class HistoryItemDao extends GenericDao<HistoryItem, Long> implements IHistoryItemDao{
    
}
