import {
    PieChart,
    Pie,
    Cell,
    Tooltip,
    Legend,
    ResponsiveContainer,
  } from "recharts";
  
  const COLORS = ["#4F8DF5", "#34D399", "#FBBF24", "#EF4444"];
  
  const ExpenseChart = ({ expenses }) => {
    const categoryMap = {};
  
    expenses.forEach((e) => {
      const key = e.categoryName || "Uncategorized";
      categoryMap[key] = (categoryMap[key] || 0) + Number(e.amount || 0);
    });
  
    const data = Object.keys(categoryMap).map((key) => ({
      name: key,
      value: categoryMap[key],
    }));
  
    if (data.length === 0) return null;
  
    return (
      <ResponsiveContainer width="100%" height={260}>
        <PieChart>
          <Pie
            data={data}
            dataKey="value"
            nameKey="name"
            cx="50%"
            cy="50%"
            innerRadius={70}
            outerRadius={110}
            paddingAngle={3}
          >
            {data.map((_, index) => (
              <Cell key={index} fill={COLORS[index % COLORS.length]} />
            ))}
          </Pie>
  
          <Tooltip />
          <Legend verticalAlign="bottom" />
        </PieChart>
      </ResponsiveContainer>
    );
  };
  
  export default ExpenseChart;