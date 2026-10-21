import java.util.*;

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {
    // 1. Merge two sorted arrays/lists into a new list: O(n + m) time and space
    public static List<Integer> merge(List<Integer> a, List<Integer> b) {
        List<Integer> res = new ArrayList<>(a.size() + b.size());
        int i = 0, j = 0;
        while (i < a.size() && j < b.size())
            res.add(a.get(i) <= b.get(j) ? a.get(i++) : b.get(j++));
        while (i < a.size()) res.add(a.get(i++));
        while (j < b.size()) res.add(b.get(j++));
        return res;
    }

    // 2. Merge two sorted linked lists, iterative: O(n + m) time, O(1) space
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(), tail = dummy;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) { tail.next = list1; list1 = list1.next; }
            else { tail.next = list2; list2 = list2.next; }
            tail = tail.next;
        }
        tail.next = (list1 != null) ? list1 : list2;
        return dummy.next;
    }

    // 3. Merge two sorted linked lists, recursive: O(n + m) time and stack space
    public ListNode mergeTwoListsRecursive(ListNode a, ListNode b) {
        if (a == null) return b;
        if (b == null) return a;
        if (a.val <= b.val) { a.next = mergeTwoListsRecursive(a.next, b); return a; }
        b.next = mergeTwoListsRecursive(a, b.next);
        return b;
    }

    // 4. Merge k sorted linked lists, min-heap: O(N log k) time, O(k) space
    public ListNode mergeKListsHeap(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));
        for (ListNode node : lists)
            if (node != null) pq.offer(node);

        ListNode dummy = new ListNode(), tail = dummy;
        while (!pq.isEmpty()) {
            ListNode min = pq.poll();
            tail.next = min;
            tail = min;
            if (min.next != null) pq.offer(min.next);
        }
        return dummy.next;
    }

    // 5. Merge k sorted linked lists, divide and conquer: O(N log k) time, O(log k) stack space
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        return divide(lists, 0, lists.length - 1);
    }

    private ListNode divide(ListNode[] lists, int lo, int hi) {
        if (lo == hi) return lists[lo];
        int mid = (lo + hi) >>> 1;
        return mergeTwoLists(divide(lists, lo, mid), divide(lists, mid + 1, hi));
    }
}

public class Main {
    static ListNode build(int... vals) {
        ListNode dummy = new ListNode(), t = dummy;
        for (int v : vals) { t.next = new ListNode(v); t = t.next; }
        return dummy.next;
    }

    static String str(ListNode n) {
        StringBuilder sb = new StringBuilder("[");
        for (; n != null; n = n.next) sb.append(n.val).append(n.next != null ? ", " : "");
        return sb.append("]").toString();
    }

    public static void main(String[] args) {
        Solution s = new Solution();

        System.out.println(Solution.merge(List.of(1, 3, 5, 7), List.of(2, 4, 6, 8, 10)));
        System.out.println(str(s.mergeTwoLists(build(1, 2, 4), build(1, 3, 4))));
        System.out.println(str(s.mergeTwoListsRecursive(build(1, 2, 4), build(1, 3, 4))));
        System.out.println(str(s.mergeKListsHeap(new ListNode[]{build(1, 4, 5), build(1, 3, 4), build(2, 6)})));
        System.out.println(str(s.mergeKLists(new ListNode[]{build(1, 4, 5), build(1, 3, 4), build(2, 6)})));
    }
}
