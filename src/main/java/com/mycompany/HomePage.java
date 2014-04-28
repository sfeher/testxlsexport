package com.mycompany;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.poi.excel.TableComponentAsXlsHandler;


public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    public HomePage(final PageParameters parameters) {
        super(parameters);
        Populate();

    }

    private void Populate() {
        TestModel entity = new TestModel();       
        WebMarkupContainer cont = new WebMarkupContainer("cont");
        final List<IColumn<?, ?>> columns = new ArrayList<IColumn<?, ?>>();
        columns.add(new PropertyColumn<TestModel, String>(new Model("id"), "id", "id"));
        columns.add(new PropertyColumn<TestModel, String>(new Model("name"), "name", "name"));
        final DataTable table = new DataTable("datatable", columns, new TestDataProvider(entity), 4);
        table.addBottomToolbar(new NavigationToolbar(table));
        cont.add(table);
        cont.add(new Link<Void>("excel") {
            @Override
            public void onClick() {
                IRequestHandler handler = new TableComponentAsXlsHandler((Component) table, "list.xls");
                RequestCycle.get().scheduleRequestHandlerAfterCurrent((IRequestHandler) handler);
            }
        });
         add(cont); 

    }

}
