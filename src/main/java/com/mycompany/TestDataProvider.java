/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

/**
 *
 * @author user
 */

public class TestDataProvider extends SortableDataProvider<TestModel,String> implements IFilterStateLocator<TestModel> {
    private static final long serialVersionUID = 1L;
    
    
    private TestModel filter=new TestModel();
    private transient List<TestModel> filtered;
    private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

    @Override
    public void detach() {
        filtered=null;
    }


    class SortableDataProviderComparator<TestModel> implements Comparator<TestModel>, Serializable {

        @Override
        public int compare(final TestModel o1, final TestModel o2) {
            PropertyModel<Comparable> model1 = new PropertyModel<Comparable>(o1, getSort().getProperty());
            PropertyModel<Comparable> model2 = new PropertyModel<Comparable>(o2, getSort().getProperty());

            int result = model1.getObject().compareTo(model2.getObject());

            if (!getSort().isAscending()) {
                result = -result;
            }

            return result;
        }
    }

    @Override
    public TestModel getFilterState() {
        return (TestModel) filter;
    }

    @Override
    public void setFilterState(TestModel t) {
        this.filter = (TestModel) t;
    }

    public TestDataProvider(TestModel filter) {
        this.filter=filter;
        this.setSort("id", SortOrder.ASCENDING);
    }

    @Override
    public Iterator<? extends TestModel> iterator(long first, long count) {
        initList();
        Collections.sort(filtered, comparator);
        return filtered.subList((int)first, Math.min((int)(first + count), filtered.size())).iterator();
    }

    @Override
    public long size() {
        initList();
        if(filtered!=null) {
           return filtered.size();    
        }
        return 0;
    }

    @Override
    public IModel<TestModel> model(final  TestModel object) {
        return new AbstractReadOnlyModel<TestModel>() {

            @Override
            public TestModel getObject() {
                return object;
            }
        };
    }

    
    private void initList() {
       if (filtered==null ||
           getFilterState()!=null    
           ) {
           filtered=new ArrayList<TestModel>();
           filtered.add(new TestModel(1,"first person"));
           filtered.add(new TestModel(2,"sec person"));
           filtered.add(new TestModel(3,"third person"));
           filtered.add(new TestModel(4,"fourth person"));
           filtered.add(new TestModel(5,"fifth person"));
           filtered.add(new TestModel(6,"sixth person"));
         }

    }
    
}
